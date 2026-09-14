package com.example.androidsprintcomposeapp.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidsprintcomposeapp.R
import com.example.androidsprintcomposeapp.ui.components.ThemedImage
import com.example.androidsprintcomposeapp.ui.theme.AndroidSprintComposeAppTheme
import com.example.androidsprintcomposeapp.ui.theme.primaryText

val images = listOf(
    R.drawable.qarmet_motivation,
    R.drawable.anabolik_motivation,
    R.drawable.qarmet_video,
)

@Composable
fun HomeScreen(
    onComputerDataClick: () -> Unit,
    onUserDataClick: () -> Unit,
    onQuoteClick: () -> Unit,
    onJokeClick: () -> Unit,
    onVideoClick: () -> Unit,
) {
    AndroidSprintComposeAppTheme {
        Scaffold(
            content = { innerPadding: PaddingValues ->
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.background),
                        contentDescription = "Background image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Column(
                        modifier = Modifier
                            .padding(paddingValues = innerPadding)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        NewsRaw(
                            onImageClick = { index ->
                                if (index == 0) {
                                    onQuoteClick()
                                }
                                if (index == 1) {
                                    onJokeClick()
                                }
                                if (index == 2) {
                                    onVideoClick()
                                }
                            }
                        )
                        CompanyLogo()
                        StudyAppHeader(
                            title = "Parsed data of Lansweeper",
                            subtitle = "from 18.06.2026"
                        )
                        Spacer(modifier = Modifier.height(25.dp))
                        MainNavButtons(
                            onComputerDataClick,
                            onUserDataClick,
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun NewsRaw(
    onImageClick: (Int) -> Unit
) {
    var selectedImage by remember { mutableIntStateOf(0) }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        itemsIndexed(images) { index, image ->
            Image(
                painter = painterResource(id = image),
                contentDescription = "Row of company news",
                modifier = Modifier
                    .size(160.dp)
                    .clip(shape = RoundedCornerShape(size=8.dp))
                    .clickable {
                        selectedImage = index
                        onImageClick(index)
                    },
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun CompanyLogo() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ThemedImage() // Logo of the company
    }
}

@Composable
fun StudyAppHeader(
    title: String = "",
    subtitle: String = ""
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(resId = R.font.montserrat_bold)),
            color = MaterialTheme.colorScheme.primaryText, // Background color
        )
        Text(
            text = subtitle,
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(resId = R.font.montserrat_bold)),
            color = MaterialTheme.colorScheme.primaryText, // Background color
        )
    }
}

@Composable
fun MainNavButtons(
    onComputerDataClick: () -> Unit,
    onUserDataClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = {
                onComputerDataClick()
            },
            modifier = Modifier
                .width(250.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryText, // Background color
            ),
        ) {
            Text(
                text = "Domain computers",
                fontFamily = FontFamily(
                    Font(resId = R.font.montserrat_bold)
                )
            )
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                imageVector = Icons.Default.Computer,
                contentDescription = "Computer icon",
                modifier = Modifier.size(26.dp)
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        Button(
            onClick = {
                onUserDataClick()
            },
            modifier = Modifier
                .width(250.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryText, // Background color
            ),
        ) {
            Text(
                text = "Domain users",
                fontFamily = FontFamily(
                    Font(resId = R.font.montserrat_bold)
                )
            )
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "User icon",
                modifier = Modifier.size(26.dp)
            )
        }
    }
}


@Composable
@Preview(showSystemUi = true, showBackground = true)
private fun StudyAppHeaderPreview() {
    StudyAppHeader()
}