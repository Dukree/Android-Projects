package com.max.threadapp

import android.view.View
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito


class TestApp {
    @Mock
    lateinit var mainact: MainActivity

    @Test
    fun onClick (view:View){
        mainact = Mockito.mock(MainActivity::class.java)
                //Mockito.verify(onClick())
    }
}