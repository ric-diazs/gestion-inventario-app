package com.example.gestion_inventario.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.Icons
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
//import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope

import com.example.gestion_inventario.navigation.Routes

@Composable
fun MainDrawer(
	navController: NavController,
    drawerState: DrawerState,
    scope: CoroutineScope,
    content: @Composable () -> Unit
){
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("InventarioApp", modifier = Modifier.padding(16.dp))
                
                HorizontalDivider()
                
                NavigationDrawerItem(
                    label = { Text(text = "Perfil") },
                    selected = false,
                    onClick = {
                        navController.navigate(Routes.ProductosAdmin.ruta)
                        scope.launch { drawerState.close() }
                    },
                    icon = {Icon(imageVector = Icons.Filled.Person, contentDescription = "Icono de perfil de usuario")}
                )

                NavigationDrawerItem(
                    label = { Text(text = "Cerrar sesión") },
                    selected = false,
                    onClick = {
                        navController.navigate(Routes.Login.ruta)
                        scope.launch { drawerState.close() }
                    }
                )
            }
        },
        content = content
    )
}