package com.yohana.echolearn.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.yohana.echolearn.R


@Composable
fun StylishImageDropdown() {
    val items = listOf(
        ImageDropdownItem(R.drawable.learning_img, "Option 1"),
        ImageDropdownItem(R.drawable.learning_img, "Option 2"),
        ImageDropdownItem(R.drawable.learning_img, "Option 3"),
        ImageDropdownItem(R.drawable.learning_img, "Option 4"),


    )
    var selectedItem by remember { mutableStateOf<ImageDropdownItem?>(null) }
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopStart) // Menempatkan menu relatif ke tombol
    ) {
        // Dropdown Button
        OutlinedTextField(
            value = selectedItem?.text ?: "",
            onValueChange = {},
            singleLine = true,
            label = { Text(text = "Select an option") },
            placeholder = { Text(text = "Choose from options") },
            leadingIcon = {
                selectedItem?.let {
                    Image(
                        painter = painterResource(id = it.imageResId),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clip(RoundedCornerShape(50))
                    )
                } ?: Image(
                    painter = painterResource(id = R.drawable.ic_person),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .clip(RoundedCornerShape(50))
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
        )

        // Dropdown Menu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
             // Menggeser dropdown ke kanan
            modifier = Modifier
                .background(Color.White, shape = RoundedCornerShape(15.dp))
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = item.imageResId),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(50))
                                    .padding(4.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = item.text,
                            )
                        }
                    },
                    onClick = {
                        selectedItem = item
                        expanded = false
                    }
                )
            }
        }
    }
}

// Data Class for Dropdown Items
data class ImageDropdownItem(
    val imageResId: Int,
    val text: String
)

@Preview(showBackground = true)
@Composable
fun SplashScreenViewPreview() {
    StylishImageDropdown()
}
