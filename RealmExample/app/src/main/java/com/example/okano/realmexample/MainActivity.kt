package com.example.okano.realmexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.realm.Realm
import io.realm.RealmResults
import java.util.*
import io.realm.RealmObjectSchema
import io.realm.DynamicRealm
import io.realm.RealmMigration
import io.realm.RealmConfiguration



class MainActivity : AppCompatActivity() {

    private lateinit var mRealm : Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Realm.init(this)
        val realmConfig = RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()
        mRealm = Realm.getInstance(realmConfig)

        // create test
        create("test1",1)
        create("test2")

        // read test
        val getData = read()
        getData.forEach {
            Log.d("debug","name :" + it.name + "price : " + it.price.toString())
        }

        // update test
        update(getData.first()!!.id, "updated")

        val getUpdatedData = read()
        getUpdatedData.forEach {
            Log.d("debug","name :" + it.name + "price : " + it.price.toString())
        }

        // delete test
        delete(getData.first()!!.id)

        val getDeletedData = read()
        getDeletedData.forEach {
            Log.d("debug","name :" + it.name + "price : " + it.price.toString())
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mRealm.close()
    }

    fun create(name:String, price:Long = 0){
        mRealm.executeTransaction {
            var book = mRealm.createObject(Book::class.java , UUID.randomUUID().toString())
            book.name = name
            book.price = price
            mRealm.copyToRealm(book)
        }
    }

    fun read() : RealmResults<Book> {
        return mRealm.where(Book::class.java).findAll()
    }

    fun update(id:String, name:String, price:Long = 0){
        mRealm.executeTransaction {
            var book = mRealm.where(Book::class.java).equalTo("id",id).findFirst()
            book!!.name = name
            if(price != 0.toLong()) {
                book.price = price
            }
        }
    }

    fun delete(id:String){
        mRealm.executeTransaction {
            var book = mRealm.where(Book::class.java).equalTo("id",id).findAll()
            book.deleteFromRealm(0)
        }
    }
}
