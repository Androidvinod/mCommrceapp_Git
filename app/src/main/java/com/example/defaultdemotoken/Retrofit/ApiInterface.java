package com.example.defaultdemotoken.Retrofit;


import com.example.defaultdemotoken.Model.CategoriesModel;
import com.example.defaultdemotoken.Model.ProductModel.ProductModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.Url;


public interface ApiInterface {
    //generate token
    //http://dkbraende.demoproject.info/rest/V1/integration/admin/token/?username=admin&password=9yWpe6v7(OZ7
    @POST()
    Call<String> token(@Url String url);


    //login data
    //http://dkbraende.demoproject.info/rest/V1/customers/me
    @GET("customers/me")
    Call<ResponseBody> loginn(@Header("Authorization") String authHeader);


    //http://app.demoproject.info/rest/V1/customers/?customer[email]=test12@yahoo.com&customer[firstname]=AA&customer[lastname]=BB&password=admin@123
    //register
    @POST()
    Call<ResponseBody> register(@Header("Authorization") String authHeader,
                                @Url String url);


    //Add to wishlist api
    //post method and customer token
    //http://dkbraende.demoproject.info/rest/V1/wishlist/add/367
    //http://dkbraende.demoproject.info/rest/V1/wishlist/add/:wishlist_item_id
    @POST()
    Call<Boolean> defaultaddtowishlist(@Header("Authorization") String authHeader, @Url String url);


    //delete api  use the delete method
    //https://app.demoproject.info/rest/V1/wishlist/delete/26
    @DELETE()
    Call<Boolean> removeitemfromWishlistt(@Header("Authorization") String authHeader,
                                          @Url String url);

    //get wish list api
    //http://dkbraende.demoproject.info/rest/V1/wishlist/items
    @GET("wishlist/items")
    Call<ResponseBody> defaultgetWishlistData(@Header("Authorization") String authHeader);


    //wishlist count
    //count wishlist api
    //get method
    //http://dkbraende.demoproject.info/rest/V1/wishlist/info
    @GET("wishlist/info")
    // http://dkbraende.demoproject.info/rest/V1/wishlist/info
    Call<ResponseBody> defaultWishlistCount(@Header("Authorization") String authHeader);


    //custome token
    @POST()
    Call<String> getcustomerToken(@Url String url);


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


    @POST()
    Call<ResponseBody> addtocart(@Header("Authorization") String token,
                                 @Url String url);

    @GET("carts/mine/items")
    Call<ResponseBody> getcartlist(@Header("Authorization") String token);

    @DELETE()
    Call<Boolean> removeFromCart(@Header("Authorization") String token,
                                @Url String url);


    //add to cart
//http://app.demoproject.info/rest/V1/carts/mine/items?cartItem[quoteId]=2&cartItem[qty]=1&cartItem[sku]=CHECKED PINAFORE DRESS
    @POST()
    Call<ResponseBody> getaddtocartapi(@Header("Authorization") String authHeader,
                                       @Url String url);

    //get quote id
//http://dkbraende.demoproject.info/rest/V1/carts/mine/?customerId=12466
    @POST()
    Call<Integer> getQuoteid(@Header("Authorization") String authHeader,
                             @Url String customer_id);

    //http://dkbraende.demoproject.info/rest/V1/customers/password?email=info@gmail.com&template=email_reset
//forget passwprd
    @PUT()
    Call<Boolean> forgetpassword(@Header("Authorization") String authHeader, @Url String url);

    //http://dkbraende.demoproject.info/rest/V1/carts/:cartId/items
    //update cart
    @POST()
    Call<ResponseBody> udatecarttt(@Header("Authorization") String authHeader,
                                @Url String url);


    //http://dkbraende.demoproject.info/rest//V1/customers/12497
    //getAddress
    //pass auth token
    //customerid
    @GET()
    Call<ResponseBody> getAddress(@Header("Authorization") String authHeader,
                                   @Url String url);



    //change password
    //http://dkbraende.demoproject.info/rest/V1/customers/me/password?currentPassword=vinod@203&newPassword=vinod@123
    //pass customer token
    //@put
    //http://dkbraende.demoproject.info/rest/V1/customers/me/password?currentPassword=Admin@123&newPassword=Admin@1234
    @PUT()
    Call<Boolean> changePassword(@Header("Authorization") String authHeader, @Url String url);


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
