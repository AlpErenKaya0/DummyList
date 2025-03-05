package com.example.task.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.task.CompanyDatas
import com.example.task.PercentageColor
import com.example.task.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Composable
fun ListScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val jsonString = remember {
        context.assets.open("companies.json").bufferedReader().use { it.readText() }
    }

    val gson = Gson()
    val companyListType = object : TypeToken<List<CompanyDatas>>() {}.type
    val companies: List<CompanyDatas> = gson.fromJson(jsonString, companyListType)


    //ÖNEMLİ NOT. GÖZÜME HOŞ GELEN ŞEKİLDE UI YAPTIM. YOKSA ANAM BABAM USULÜ OLUŞTURULMAMASI GEREKTİĞİNİ, BİR FONTFAMİLY OLUŞTURULMASI GEREKTİĞİNİ
    //DİL DEĞİŞİKLİKLERİ İÇİN METİNLERİN STATİK KALMAMASI GEREKTİĞİNİ VE STRİNGLERİN OLUŞTURULMASI GEREKTİĞİNİ, HEM İNGİLİZCE HEM TURKÇE OLUŞTURULŞMASI GEREKTİĞİNİ BİLİYORUM
    // AYNI ZAMANDA EKRAN BOYUTLANDIRMASI İÇİN SCREENWİDTH VE SCREEN HEİGHT ALINIR VE GÖRÜNÜMLER BUNA GÖRE, BU DİMENLERE GÖRE OLUŞTURULUR.
    // FİLLMAXWİDTH/HEİGHT İŞE YARAMAZ. ÖRNEK KOD İÇİN => https://github.com/AlpErenKaya0/ResponsiveScreenTest,
    //DAHA ANAM BABAM USULÜ ÖRNEK İÇİN => WHEREİSMYCARAPP VEYA LATESTMVVMBLUEPRİNT'E BAKABİLİRSİNİZ.
    //TEST, (KMM İÇİN ÖZELLİKLE KOİN İLE) DI, COROUTİNESSCOPE'LARLA THREAD AYRIŞTIRMALARI, ANIMATIONLAR (BASİT BİR YERÇEKİMİ VE FADING ANIMASYONU İÇİN DE
    //https://github.com/AlpErenKaya0/LatestMVVMBluePrint İNCELEYEBİLİRSİNİZ.
    //VİEWMODEL, API İSTEKLERİNİN VE USE-CASE'LERİN VS VS EKLENMESİ GEREKTİĞİNİ DE BİLİYORUM.
    //VERİLERİN sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    //    class Success<T>(data: T) : Resource<T>(data)
    //    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    //    class Loading<T>(data: T? = null) : Resource<T>(data)
    //} İLE HANDLE EDİLMESİ GEREKTİĞİNİ DE BİLİYORUM.


    //NOT => Tabii ki de 200 metre şeklinde almak kolay, önemli olan =>48.9694459, gibi enlem boylamdan
    // güncel konumu çıkartarak konum bilgisini bulmak. İstediğiniz şey sadece UI olduğu için Dummy Konum bilgisi ile bunu hesaplama işlemini yapmadım
    //isterseniz yapabilirim veya buna örnek kodu yapıp atabilirim.

    Column(modifier = Modifier.fillMaxWidth().padding(top = 12.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Restoranlar",
                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 24.sp),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "(Yakindan Uzaga Gore Sirala)",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
                fontWeight = FontWeight.Thin,
            )
            //İsterseniz Text'e clickable'lık ekleyip bir fonksiyonda önce async şekilde çektiğim Json dosyasındaki itemları yine
            //async bir şekilde sıralayıp, VM 'den ekrana aktarma işlevini ekleyebilirim.
        }

        LazyColumn(modifier = modifier) {
            items(companies) { company ->
                val context = LocalContext.current
                //. dan sonra jpeg, jpg , png vs olabileceği için gpt'ye noktadan sonrasını poplamayı sordum ama kendi yaptığım =>
                //val companyImage = company.companyImage.replace(".png", "")
                //bu şekilde.
                val companyImage = company.companyImage.substringBeforeLast(".")
                val resourceId =
                    context.resources.getIdentifier(companyImage, "drawable", context.packageName)
                val imagePainter =
                    painterResource(id = if (resourceId != 0) resourceId else R.drawable.hummus)

                val percentageColor = PercentageColor.fromPercentage(company.percentage)

                val boxColor = when (percentageColor) {
                    PercentageColor.RED -> Color(0xFFFF4C4C)
                    PercentageColor.YELLOW -> Color(0xFFFFC107)
                    PercentageColor.GREEN -> Color(0xFF4CAF50)
                }


                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    shape = CardDefaults.outlinedShape,
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier.padding(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = imagePainter,
                            contentDescription = "Company image",
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        //Image cliplenecek veya Fit vs AsyncImage da kullanılabilir
                        //zaten normalde veri Internetten çekilecek sonuçta
                        //NOT => Denendi , AsyncImage buna uygun bir veri tipi tutamıyor, Zaten Image ile yapılabildiği için
                        //Image yerine AsyncImage verimliliği Artırır gibi düşünmüştüm yanlış düşünmüşüm.
                        Spacer(modifier = Modifier.width(4.dp))
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = company.name,
                                style = MaterialTheme.typography.displaySmall.copy(fontSize = 26.sp),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (company.percentage != null) {
                                    Box(
                                        modifier = Modifier
                                            .height(24.dp)
                                            .background(boxColor)
                                            .padding(horizontal = 8.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            color = Color.White,
                                            text = "%${(company.percentage * 100).toInt()}",
                                            style = MaterialTheme.typography.titleSmall.copy(
                                                fontSize = 16.sp
                                            )
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(4.dp))
                                }
                                company.food.forEach { foodItem ->
                                    Text(
                                        text = "• $foodItem",
                                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
                                        modifier = Modifier.padding(end = 2.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                                    contentDescription = "Bu metinler görme engelliler için biliyorum",
                                    modifier = Modifier.size(8.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = company.distance.toString(),
                                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(
                                    text = "Metre",
                                    style = MaterialTheme.typography.headlineSmall.copy(fontSize = 16.sp),
                                    fontWeight = FontWeight.Normal
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "|",
                                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Image(
                                    painter = painterResource(R.drawable.ic_launcher_foreground),
                                    contentDescription = "Android logo",
                                    modifier = Modifier.size(48.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "%${(company.likes * 100).toInt()}",
                                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp),
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                if (company.comments > 500) {
                                    Text(
                                        text = "(500+)",
                                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 12.sp),
                                        fontWeight = FontWeight.Thin
                                    )
                                } else {
                                    Text(
                                        text = "(${company.comments})",
                                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 12.sp),
                                        fontWeight = FontWeight.Thin
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
//Preview  Makalesinde =>
// @Preview and large data sets
// kısmındaki gibi veri oluşturularak kullanma işlemi vs
//yapılabilir
//ben yine de render problem olan bir preview bırakayım dedim
// yoksa çalışmadığını ve çalışmayacağını biliyorum.
//ya da öncesinde json veriyi bir array'e çıkarıp bu array'i aynı zamanda ListScreen'in parantezi içine vererek
// kavramı isim olarak bilmiyorum constructor olarak vermek diyeceğim parantezin içine vermeyi, preview'da da
//parantez içine vererek dummy datasetle vs oluşturulabilir, bunun yerine testing/test/ test kodu yazmak yapmak/eklemek daha mantıklı tabii.
@Preview(showBackground = true)
@Composable
fun ListScreenPreview() {
    ListScreen()
}