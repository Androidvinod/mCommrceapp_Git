package com.example.defaultdemotoken.Retrofit;




import com.example.defaultdemotoken.Model.CategoriesModel;
import com.example.defaultdemotoken.Model.ProductModel.ProductModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;


public interface ApiInterface {



    //http://app.demoproject.info/rest/V1/integration/admin/token/?username=admin&password=juLaGh18lAJC
    //for getting token  key
    //login
    @GET("categories")
    Call<CategoriesModel> categories(@Header("Authorization") String authHeader);

    //http://app.demoproject.info/rest/all/V1/products?searchCriteria[filterGroups][0][filters][0][field]=category_id&searchCriteria[filterGroups][0][filters][0][value]=8
    @GET("products")
    Call<ProductModel> products(@Header("Authorization") String authHeader,
                                @Query("searchCriteria[filterGroups][0][filters][0][field]") String categoryid,
                                @Query("searchCriteria[filterGroups][0][filters][0][value]") String id);

    //generate token
    //http://dkbraende.demoproject.info/rest/V1/integration/admin/token/?username=admin&password=9yWpe6v7(OZ7
    @POST()
    Call<String> token(@Url String url);


    /*
     Payment Methods
	/V1/carts/{cartId}/payment-methods
	//http://dkbraende.demoproject.info/rest//V1/carts/162919/payment-methods

    Shipping Method
	/V1/carts/mine/shipping-methods
	//http://dkbraende.demoproject.info/rest//V1/carts/mine/shipping-methods

	DELETE 	/V1/addresses/{addressId}
 */


}
