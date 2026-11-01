package cl.duoc.corpaliv_grupo9.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import cl.duoc.corpaliv_grupo9.model.CarouselItem
import cl.duoc.corpaliv_grupo9.ui.theme.White

@Composable
fun HeroCarousel(
    items: List<CarouselItem>,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (items.isEmpty()) return

    val colors = MaterialTheme.colorScheme
    val pagerState = rememberPagerState(pageCount = { items.size })

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(360.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(colors.scrim)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val item = items[page]

            Box(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = item.imageUrl,
                    contentDescription = item.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.78f)
                        .align(Alignment.TopCenter)
                        .graphicsLayer { alpha = 1f }
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                0f to Color.Transparent,
                                0.18f to Color.Transparent,
                                0.42f to colors.scrim.copy(alpha = 0.18f),
                                0.62f to colors.scrim.copy(alpha = 0.62f),
                                0.74f to colors.scrim.copy(alpha = 0.94f),
                                0.78f to colors.scrim,
                                1f to colors.scrim
                            )
                        )
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 22.dp, vertical = 22.dp)
                ) {
                    Spacer(Modifier.weight(1f))

                    Text(
                        text = item.tag.uppercase(),
                        color = White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.7.sp
                    )
                    Spacer(Modifier.height(10.dp))

                    Text(
                        text = item.title,
                        color = White,
                        fontSize = 29.sp,
                        lineHeight = 32.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = item.description,
                        color = White.copy(alpha = 0.9f),
                        fontSize = 14.sp,
                        lineHeight = 19.sp
                    )
                    Spacer(Modifier.height(18.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = { onNavigate(item.destination) },
                            shape = RoundedCornerShape(24.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = White,
                                contentColor = colors.inverseOnSurface
                            ),
                            contentPadding = PaddingValues(start = 16.dp, end = 7.dp),
                            modifier = Modifier.height(48.dp)
                        ) {
                            Text(
                                text = item.buttonText,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(Modifier.width(10.dp))
                            Box(
                                modifier = Modifier
                                    .size(34.dp)
                                    .clip(CircleShape)
                                    .background(colors.primary),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = null,
                                    tint = colors.onPrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        Spacer(Modifier.weight(1f))
                        HeroPageIndicator(pagerState = pagerState, pageCount = items.size)
                    }
                }
            }
        }
    }
}

@Composable
private fun HeroPageIndicator(
    pagerState: PagerState,
    pageCount: Int
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) { index ->
            val isSelected = pagerState.currentPage == index
            val width by animateDpAsState(
                targetValue = if (isSelected) 20.dp else 6.dp,
                label = "heroPageIndicatorWidth"
            )
            Box(
                modifier = Modifier
                    .width(width)
                    .height(6.dp)
                    .clip(CircleShape)
                    .background(if (isSelected) White else White.copy(alpha = 0.5f))
            )
        }
    }
}
