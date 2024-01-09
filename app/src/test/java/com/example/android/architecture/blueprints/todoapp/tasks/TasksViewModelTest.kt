package com.example.android.architecture.blueprints.todoapp.tasks

import android.os.Build.VERSION_CODES.Q
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.nullValue
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Q])
class TasksViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var tasksViewModel: TasksViewModel

    @Before
    fun setupViewModel() {
        // Given a fresh ViewModel
        tasksViewModel = TasksViewModel(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun addNewTask_setsNewTaskEvent() {
        // When adding a new task
        tasksViewModel.addNewTask()

        // Then the new task event is triggered
        val value = tasksViewModel.newTaskEvent.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled(), not(nullValue()))

    }

    @Test
    fun setFilterAllTasks_tasksAddViewVisible() {
        // When adding a new task
        tasksViewModel.setFiltering(TasksFilterType.ALL_TASKS)

        // Then the new task event is triggered
        val value = tasksViewModel.tasksAddViewVisible.getOrAwaitValue()

        assertThat(value, `is`(true))
        Assert.assertEquals(value, true)

    }

}

/*
Como seria implementação sem o liveDataTestUtil
     val observer = Observer<Event<Unit>> {}

       //G
       val tasksViewModel = TasksViewModel(ApplicationProvider.getApplicationContext())

       try {

           // Observe the LiveData forever
           tasksViewModel.newTaskEvent.observeForever(observer)

           //W
           tasksViewModel.addNewTask()

           //T
           val value = tasksViewModel.newTaskEvent.value
           assertThat(value?.getContentIfNotHandled(), (not(nullValue())))
       } finally {
           tasksViewModel.newTaskEvent.removeObserver(observer)
       }*/