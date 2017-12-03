package com.example.okano.realmexample

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import java.util.*

/**
 * Created by okano on 2017/12/02.
 */
open class Book(
        @PrimaryKey open var id : String = UUID.randomUUID().toString(),
        @Required open var name : String = "",
        open var price : Long = 0
) : RealmObject() {}