package com.nantesmatthew.serinotechexam.products.data.repository

import android.util.Log
import androidx.paging.*
import com.nantesmatthew.serinotechexam.products.data.data_source.ProductApi
import com.nantesmatthew.serinotechexam.products.data.data_source.ProductDao
import com.nantesmatthew.serinotechexam.products.data.data_source.RemoteKeysDao
import com.nantesmatthew.serinotechexam.products.data.entities.*
import com.nantesmatthew.serinotechexam.products.domain.Product

const val  FIRST_PAGE = 1

@OptIn(ExperimentalPagingApi::class)
class ProductRemoteMediator(private val api: ProductApi,
                            private val remoteDao:RemoteKeysDao,
                            private val productDao:ProductDao
                            ):RemoteMediator<Int,ProductLocalEntity>() {


    override suspend fun initialize() = InitializeAction.LAUNCH_INITIAL_REFRESH
    companion object{
        private const val TAG = "ProductRemoteMediator"
    }
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ProductLocalEntity>
    ): MediatorResult {
        Log.d(TAG, "load: test")
            val page = when(loadType){
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: FIRST_PAGE
                }
                LoadType.PREPEND ->{
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    // If remoteKeys is null, that means the refresh result is not in the database yet.
                    // We can return Success with `endOfPaginationReached = false` because Paging
                    // will call this method again if RemoteKeys becomes non-null.
                    // If remoteKeys is NOT NULL but its prevKey is null, that means we've reached
                    // the end of pagination for prepend.
                  remoteKeys?.prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)

                }
                LoadType.APPEND ->{
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    // If remoteKeys is null, that means the refresh result is not in the database yet.
                    // We can return Success with `endOfPaginationReached = false` because Paging
                    // will call this method again if RemoteKeys becomes non-null.
                    // If remoteKeys is NOT NULL but its nextKey is null, that means we've reached
                    // the end of pagination for append.
                     remoteKeys?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)


                }
            }
        Log.d(TAG, "load: $page")

        return try {
            val response = api.products(skip = if (page == FIRST_PAGE)0 else  state.config.pageSize * page -1,limit = state.config.pageSize * page)
            val productNetworkEntities = response.body()?.products
            val endOfPaginationReached = productNetworkEntities?.isEmpty() ?: true

                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    remoteDao.clearRemoteKeys()
                    productDao.deleteAll()
                }
                val prevKey = if (page == FIRST_PAGE) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = productNetworkEntities?.map {
                    RemoteKeys(productId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                remoteDao.insertAll(keys ?: emptyList())
                productDao.saveProducts(productNetworkEntities?.map { it.toLocalEntity() } ?: emptyList())
            Log.d(TAG, "load: ${productNetworkEntities?.size}")
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (e: Exception) {
            Log.d(TAG, "load: ${e.localizedMessage}")
            MediatorResult.Error(e)
        }



    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ProductLocalEntity>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { product ->
                // Get the remote keys of the last item retrieved
                remoteDao.remoteKeysRepoId(product.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ProductLocalEntity>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { product ->
                // Get the remote keys of the first items retrieved
               remoteDao.remoteKeysRepoId(product.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, ProductLocalEntity>
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { productId ->
                remoteDao.remoteKeysRepoId(productId)
            }
        }
    }
}