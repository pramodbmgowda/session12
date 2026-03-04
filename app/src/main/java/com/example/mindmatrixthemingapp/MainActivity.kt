package com.example.mindmatrixthemingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mindmatrixthemingapp.ui.theme.MindMatrixThemingAppTheme

// Data Model
data class CardItem(val id: Int, val title: String, val description: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MindMatrixThemingAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AnimatedCardGallery()
                }
            }
        }
    }
}

@Composable
fun AnimatedCardGallery() {
    val items = listOf(
        CardItem(1, "Mountain Retreat", "A beautiful cabin in the snowy mountains. Perfect for a weekend getaway. Features include a hot tub, fireplace, and scenic hiking trails nearby."),
        CardItem(2, "Ocean View Villa", "Wake up to the sound of crashing waves. This villa offers private beach access, a sunset balcony, and modern amenities for ultimate relaxation.")
    )

    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        items(items) { item ->
            ExpandableThemedCard(item)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ExpandableThemedCard(item: CardItem) {
    var isExpanded by remember { mutableStateOf(false) }
    var isFavourite by remember { mutableStateOf(false) }

    val favIconColor by animateColorAsState(
        targetValue = if (isFavourite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
        label = "favoriteColorAnimation"
    )

    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            .clickable { isExpanded = !isExpanded }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                IconButton(onClick = { isFavourite = !isFavourite }) {
                    Icon(
                        imageVector = if (isFavourite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = favIconColor
                    )
                }
            }

            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}