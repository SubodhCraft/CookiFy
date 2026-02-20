//package com.example.cookify.repository
//
//import com.example.cookify.model.ProductModel
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase
//
//class ProductRepoImpl : ProductRepo {
//    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
//
//    val ref: DatabaseReference = database.getReference("products")
//
//    override fun addProduct(
//        model: ProductModel,
//        callback: (Boolean, String) -> Unit
//    ) {
//        var id = ref.push().key.toString()
//        model.productId = id
//
//        ref.child(id).setValue(model).addOnCompleteListener {
//            if(it.isSuccessful){
//                callback(true,"product added")
//            }else{
//                callback(false,"${it.exception?.message}")
//
//            }
//        }    }
//
//    override fun updateProduct(
//        model: ProductModel,
//        callback: (Boolean, String) -> Unit
//    ) {
//        TODO("Not yet implemented")
//    }
//
//    override fun deleteProduct(
//        productId: String,
//        callback: (Boolean, String) -> Unit
//    ) {
//        TODO("Not yet implemented")
//    }
//
//    override fun getAllProduct(callback: (Boolean, String, List<ProductModel>?) -> Unit) {
//        TODO("Not yet implemented")
//    }
//
//    override fun getProductById(
//        productId: String,
//        callback: (Boolean, String, ProductModel?) -> Unit
//    ) {
//        TODO("Not yet implemented")
//    }
//
//    override fun getProductByCategory(
//        categoryId: String,
//        callback: (Boolean, String, List<ProductModel>?) -> Unit
//    ) {
//        TODO("Not yet implemented")
//    }
//}