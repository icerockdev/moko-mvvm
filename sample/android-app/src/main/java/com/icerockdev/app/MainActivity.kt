/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.icerockdev.app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.icerockdev.app.sample1.SimpleActivity
import com.icerockdev.app.sample2.EventsActivity
import com.icerockdev.app.sample3.EventsOwnerActivity
import com.icerockdev.app.sample4.ValidationMergeActivity
import com.icerockdev.app.sample5.ValidationAllActivity
import com.icerockdev.app.sample6.LoginActivity
import com.icerockdev.app.sample7.ContainerActivity
import kotlin.reflect.KClass

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
    }

    fun onSample1ButtonPressed(view: View) {
        openScreen(SimpleActivity::class)
    }

    fun onSample2ButtonPressed(view: View) {
        openScreen(EventsActivity::class)
    }

    fun onSample3ButtonPressed(view: View) {
        openScreen(EventsOwnerActivity::class)
    }

    fun onSample4ButtonPressed(view: View) {
        openScreen(ValidationMergeActivity::class)
    }

    fun onSample5ButtonPressed(view: View) {
        openScreen(ValidationAllActivity::class)
    }

    fun onSample6ButtonPressed(view: View) {
        openScreen(LoginActivity::class)
    }

    fun onSample7ButtonPressed(view: View) {
        openScreen(ContainerActivity::class)
    }

    private fun openScreen(clazz: KClass<out Activity>) {
        val intent = Intent(this, clazz.java)
        startActivity(intent)
    }
}