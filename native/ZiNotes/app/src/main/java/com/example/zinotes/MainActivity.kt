package com.example.zinotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.zinotes.ui.AddScreen
import com.example.zinotes.ui.DetailScreen
import com.example.zinotes.ui.EditScreen
import com.example.zinotes.ui.ListScreen
import com.example.zinotes.ui.theme.ZiNotesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZiNotesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)){
                        Image(
                            painter = painterResource(R.drawable.background),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.FillBounds,
                        )
                        ZiNotesApp(modifier = Modifier.fillMaxSize())
                    }
                }
            }
        }
    }
}

@Composable
fun ZiNotesApp(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "list"
    ) {
        // LIST SCREEN
        composable(route = "list") {
            ListScreen(
                modifier = modifier,
                onItemClick = { hanziId ->
                    navController.navigate("detail/$hanziId")
                },
                onAddClick = {
                    navController.navigate("add")
                },
            )
        }
        // ADD SCREEN
        composable(route = "add") {
            AddScreen(
                onNavigateBack = {navController.popBackStack()}
            )
        }
        // DETAIL SCREEN
        composable(
            route = "detail/{hanziId}",
            arguments = listOf(navArgument("hanziId") { type = NavType.StringType })
        ){ backStackEntry ->
            val hanziId = backStackEntry.arguments?.getString("hanziId")
            DetailScreen(
                hanziId = hanziId,
                onNavigateToEdit = {hanziId ->
                    navController.navigate("edit/$hanziId")
                },
                onNavigateBack = {navController.popBackStack()}
            )
        }
        // EDIT SCREEN
        composable(
            route = "edit/{hanziId}",
            arguments = listOf(navArgument("hanziId") { type = NavType.StringType })
        ){ backStackEntry ->
            val hanziId = backStackEntry.arguments?.getString("hanziId")
            EditScreen(
                hanziId = hanziId,
                onNavigateBack = {navController.popBackStack()}
            )
        }
    }


}

@Preview
@Composable
fun AppPreview() {
    ZiNotesTheme {
        ZiNotesApp()
    }
}