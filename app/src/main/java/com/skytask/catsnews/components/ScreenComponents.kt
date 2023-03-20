package com.skytask.catsnews.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.skytask.catsnews.R
import com.skytask.catsnews.components.theme.Background
import com.skytask.catsnews.components.theme.OnSurface
import com.skytask.catsnews.components.theme.catsTypography

@Composable
fun CatsScreen(
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Background)
    )
    Box(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
    ) {
        content()
    }
}

@Composable
fun EmptyList(text: String = stringResource(R.string.thereAreNoItems)) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        text = text,
        color = Background,
        textAlign = TextAlign.Center,
        style = catsTypography.subtitle1
    )
}

@Composable
fun Header(
    start: (@Composable () -> Unit)?,
    center: (@Composable () -> Unit)?
) {
    Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (start != null) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) { start() }
        }
        if (center != null) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) { center() }
        }
    }
}

@Composable
fun HeaderButtonBack(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(R.drawable.header_button_back),
            contentDescription = "",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun HeaderTitle(title: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = OnSurface,
            textAlign = TextAlign.Center,
            style = catsTypography.h3
        )
    }
}

@Composable
fun NewsImage(
    modifier: Modifier,
    contentDescription: String = "",
    imageUrl: String = ""

) {
    if (imageUrl.isEmpty()) {
        Image(
            painter = painterResource(R.drawable.default_img),
            modifier = modifier,
            contentDescription = contentDescription,
        )
    } else {
        AsyncImage(
            model = imageUrl,
            modifier = modifier,
            contentDescription = contentDescription,
            error = painterResource(R.drawable.default_img),
        )
    }
}
