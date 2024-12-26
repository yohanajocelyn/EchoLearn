package com.yohana.echolearn.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.yohana.echolearn.ui.theme.EchoLearnTheme

@Composable
fun ListeningView(){
    LazyColumn {
        item {
            Row {
                //album img
                Column {
                    //judul
                    //toggle button
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListeningViewPreview() {
    EchoLearnTheme {
        ListeningView()
    }
}