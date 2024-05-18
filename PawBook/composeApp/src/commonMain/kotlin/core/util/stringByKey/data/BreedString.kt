package core.util.stringByKey.data

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import pawbook.composeapp.generated.resources.*

object BreedString {
    @OptIn(ExperimentalResourceApi::class)
    fun getBreedStringResource(key: String): StringResource {
        when(key) {
            "english_setter" -> return Res.string.app_name
            "kerry_blue_terrier" -> return Res.string.kerry_blue_terrier
            "cairn_terrier" -> return Res.string.cairn_terrier
            "english_cocker_spaniel" -> return Res.string.english_cocker_spaniel
            "gordon_setter" -> return Res.string.gordon_setter
            "airedale_terrier" -> return Res.string.airedale_terrier
            "australian_terrier" -> return Res.string.australian_terrier
            "bedlington_terrier" -> return Res.string.bedlington_terrier
            "border_terrier" -> return Res.string.border_terrier
            "bull_terrier" -> return Res.string.bull_terrier
            "fox_terrier_smooth" -> return Res.string.fox_terrier_smooth
            "english_toy_terrier_black_and_tan" -> return Res.string.english_toy_terrier_black_and_tan
            "swedish_vallhund" -> return Res.string.swedish_vallhund
            "belgian_shepherd_dog" -> return Res.string.belgian_shepherd_dog
            "old_english_sheepdog" -> return Res.string.old_english_sheepdog
            "griffon_nivernais" -> return Res.string.griffon_nivernais
            "briquet_griffon_vendeen" -> return Res.string.briquet_griffon_vendeen
            "ariegeois" -> return Res.string.ariegeois
            "gascon_saintongeois" -> return Res.string.gascon_saintongeois
            "great_gascony_blue" -> return Res.string.great_gascony_blue
            "poitevin" -> return Res.string.poitevin
            "billy" -> return Res.string.billy
            "artois_hound" -> return Res.string.artois_hound
            "porcelaine" -> return Res.string.porcelaine
            "small_blue_gascony" -> return Res.string.small_blue_gascony
            "blue_gascony_griffon" -> return Res.string.blue_gascony_griffon
            "grand_basset_griffon_vendeen" -> return Res.string.grand_basset_griffon_vendeen
            "norman_artesien_basset" -> return Res.string.norman_artesien_basset
            "blue_gascony_basset" -> return Res.string.blue_gascony_basset
            "basset_fauve_de_bretagne" -> return Res.string.basset_fauve_de_bretagne
            "portuguese_water_dog" -> return Res.string.portuguese_water_dog
            "welsh_corgi_cardigan" -> return Res.string.welsh_corgi_cardigan
            "welsh_corgi_pembroke" -> return Res.string.welsh_corgi_pembroke
            "irish_soft_coated_wheaten_terrier" -> return Res.string.irish_soft_coated_wheaten_terrier
            "yugoslavian_shepherd_dog_sharplanina" -> return Res.string.yugoslavian_shepherd_dog_sharplanina
            "jämthund" -> return Res.string.jämthund
            "basenji" -> return Res.string.basenji
            "beauce_sheepdog" -> return Res.string.beauce_sheepdog
            "bernese_mountain_dog" -> return Res.string.bernese_mountain_dog
            "appenzell_cattle_dog" -> return Res.string.appenzell_cattle_dog
            "entlebuch_cattle_dog" -> return Res.string.entlebuch_cattle_dog
            "karelian_bear_dog" -> return Res.string.karelian_bear_dog
            "finnish_spitz" -> return Res.string.finnish_spitz
            "newfoundland" -> return Res.string.newfoundland
            "finnish_hound" -> return Res.string.finnish_hound
            "polish_hound" -> return Res.string.polish_hound
            "komondor" -> return Res.string.komondor
            "kuvasz" -> return Res.string.kuvasz
            "puli" -> return Res.string.puli
            "pumi" -> return Res.string.pumi
            "hungarian_short_haired_pointer_vizsla" -> return Res.string.hungarian_short_haired_pointer_vizsla
            "great_swiss_mountain_dog" -> return Res.string.great_swiss_mountain_dog
            "swiss_hound" -> return Res.string.swiss_hound
            "small_swiss_hound" -> return Res.string.small_swiss_hound
            "st_bernard" -> return Res.string.st_bernard
            "coarse_haired_styrian_hound" -> return Res.string.coarse_haired_styrian_hound
            "austrian_black_and_tan_hound" -> return Res.string.austrian_black_and_tan_hound
            "austrian_pinscher" -> return Res.string.austrian_pinscher
            "maltese" -> return Res.string.maltese
            "fawn_brittany_griffon" -> return Res.string.fawn_brittany_griffon
            "petit_basset_griffon_vendeen" -> return Res.string.petit_basset_griffon_vendeen
            "tyrolean_hound" -> return Res.string.tyrolean_hound
            "lakeland_terrier" -> return Res.string.lakeland_terrier
            "manchester_terrier" -> return Res.string.manchester_terrier
            "norwich_terrier" -> return Res.string.norwich_terrier
            "scottish_terrier" -> return Res.string.scottish_terrier
            "sealyham_terrier" -> return Res.string.sealyham_terrier
            "skye_terrier" -> return Res.string.skye_terrier
            "staffordshire_bull_terrier" -> return Res.string.staffordshire_bull_terrier
            "continental_toy_spaniel" -> return Res.string.continental_toy_spaniel
            "welsh_terrier" -> return Res.string.welsh_terrier
            "griffon_bruxellois" -> return Res.string.griffon_bruxellois
            "griffon_belge" -> return Res.string.griffon_belge
            "petit_brabançon" -> return Res.string.petit_brabançon
            "schipperke" -> return Res.string.schipperke
            "bloodhound" -> return Res.string.bloodhound
            "west_highland_white_terrier" -> return Res.string.west_highland_white_terrier
            "yorkshire_terrier" -> return Res.string.yorkshire_terrier
            "catalan_sheepdog" -> return Res.string.catalan_sheepdog
            "shetland_sheepdog" -> return Res.string.shetland_sheepdog
            "ibizan_podenco" -> return Res.string.ibizan_podenco
            "burgos_pointing_dog" -> return Res.string.burgos_pointing_dog
            "spanish_mastiff" -> return Res.string.spanish_mastiff
            "pyrenean_mastiff" -> return Res.string.pyrenean_mastiff
            "portuguese_sheepdog" -> return Res.string.portuguese_sheepdog
            "portuguese_warren_hound_portuguese_podengo" -> return Res.string.portuguese_warren_hound_portuguese_podengo
            "brittany_spaniel" -> return Res.string.brittany_spaniel
            "rafeiro_of_alentejo" -> return Res.string.rafeiro_of_alentejo
            "german_spitz" -> return Res.string.german_spitz
            "german_wire_haired_pointing_dog" -> return Res.string.german_wire_haired_pointing_dog
            "weimaraner" -> return Res.string.weimaraner
            "westphalian_dachsbracke" -> return Res.string.westphalian_dachsbracke
            "french_bulldog" -> return Res.string.french_bulldog
            "kleiner_münsterländer" -> return Res.string.kleiner_münsterländer
            "german_hunting_terrier" -> return Res.string.german_hunting_terrier
            "german_spaniel" -> return Res.string.german_spaniel
            "french_water_dog" -> return Res.string.french_water_dog
            "blue_picardy_spaniel" -> return Res.string.blue_picardy_spaniel
            "wire_haired_pointing_griffon_korthals" -> return Res.string.wire_haired_pointing_griffon_korthals
            "picardy_spaniel" -> return Res.string.picardy_spaniel
            "clumber_spaniel" -> return Res.string.clumber_spaniel
            "curly_coated_retriever" -> return Res.string.curly_coated_retriever
            "golden_retriever" -> return Res.string.golden_retriever
            "briard" -> return Res.string.briard
            "pont_audemer_spaniel" -> return Res.string.pont_audemer_spaniel
            "saint_germain_pointer" -> return Res.string.saint_germain_pointer
            "dogue_de_bordeaux" -> return Res.string.dogue_de_bordeaux
            "deutsch_langhaar" -> return Res.string.deutsch_langhaar
            "large_munsterlander" -> return Res.string.large_munsterlander
            "german_short_haired_pointing_dog" -> return Res.string.german_short_haired_pointing_dog
            "irish_red_setter" -> return Res.string.irish_red_setter
            "flat_coated_retriever" -> return Res.string.flat_coated_retriever
            "labrador_retriever" -> return Res.string.labrador_retriever
            "field_spaniel" -> return Res.string.field_spaniel
            "irish_water_spaniel" -> return Res.string.irish_water_spaniel
            "english_springer_spaniel" -> return Res.string.english_springer_spaniel
            "welsh_springer_spaniel" -> return Res.string.welsh_springer_spaniel
            "sussex_spaniel" -> return Res.string.sussex_spaniel
            "king_charles_spaniel" -> return Res.string.king_charles_spaniel
            "smålandsstövare" -> return Res.string.smålandsstövare
            "drever" -> return Res.string.drever
            "schillerstövare" -> return Res.string.schillerstövare
            "hamiltonstövare" -> return Res.string.hamiltonstövare
            "french_pointing_dog_gascogne_type" -> return Res.string.french_pointing_dog_gascogne_type
            "french_pointing_dog_pyrenean_type" -> return Res.string.french_pointing_dog_pyrenean_type
            "swedish_lapphund" -> return Res.string.swedish_lapphund
            "cavalier_king_charles_spaniel" -> return Res.string.cavalier_king_charles_spaniel
            "pyrenean_mountain_dog" -> return Res.string.pyrenean_mountain_dog
            "pyrenean_sheepdog_smooth_faced" -> return Res.string.pyrenean_sheepdog_smooth_faced
            "irish_terrier" -> return Res.string.irish_terrier
            "boston_terrier" -> return Res.string.boston_terrier
            "long_haired_pyrenean_sheepdog" -> return Res.string.long_haired_pyrenean_sheepdog
            "slovakian_chuvach" -> return Res.string.slovakian_chuvach
            "dobermann" -> return Res.string.dobermann
            "boxer" -> return Res.string.boxer
            "leonberger" -> return Res.string.leonberger
            "rhodesian_ridgeback" -> return Res.string.rhodesian_ridgeback
            "rottweiler" -> return Res.string.rottweiler
            "dachshund" -> return Res.string.dachshund
            "bulldog" -> return Res.string.bulldog
            "serbian_hound" -> return Res.string.serbian_hound
            "istrian_short_haired_hound" -> return Res.string.istrian_short_haired_hound
            "istrian_wire_haired_hound" -> return Res.string.istrian_wire_haired_hound
            "dalmatian" -> return Res.string.dalmatian
            "posavatz_hound" -> return Res.string.posavatz_hound
            "bosnian_broken_haired_hound_called_barak" -> return Res.string.bosnian_broken_haired_hound_called_barak
            "collie_rough" -> return Res.string.collie_rough
            "bullmastiff" -> return Res.string.bullmastiff
            "greyhound" -> return Res.string.greyhound
            "english_foxhound" -> return Res.string.english_foxhound
            "irish_wolfhound" -> return Res.string.irish_wolfhound
            "beagle" -> return Res.string.beagle
            "whippet" -> return Res.string.whippet
            "basset_hound" -> return Res.string.basset_hound
            "deerhound" -> return Res.string.deerhound
            "italian_spinone" -> return Res.string.italian_spinone
            "german_shepherd_dog" -> return Res.string.german_shepherd_dog
            "american_cocker_spaniel" -> return Res.string.american_cocker_spaniel
            "dandie_dinmont_terrier" -> return Res.string.dandie_dinmont_terrier
            "fox_terrier_wire" -> return Res.string.fox_terrier_wire
            "castro_laboreiro_dog" -> return Res.string.castro_laboreiro_dog
            "bouvier_des_ardennes" -> return Res.string.bouvier_des_ardennes
            "poodle" -> return Res.string.poodle
            "estrela_mountain_dog" -> return Res.string.estrela_mountain_dog
            "french_spaniel" -> return Res.string.french_spaniel
            "picardy_sheepdog" -> return Res.string.picardy_sheepdog
            "ariege_pointing_dog" -> return Res.string.ariege_pointing_dog
            "bourbonnais_pointing_dog" -> return Res.string.bourbonnais_pointing_dog
            "auvergne_pointer" -> return Res.string.auvergne_pointer
            "giant_schnauzer" -> return Res.string.giant_schnauzer
            "schnauzer" -> return Res.string.schnauzer
            "miniature_schnauzer" -> return Res.string.miniature_schnauzer
            "german_pinscher" -> return Res.string.german_pinscher
            "miniature_pinscher" -> return Res.string.miniature_pinscher
            "affenpinscher" -> return Res.string.affenpinscher
            "portuguese_pointing_dog" -> return Res.string.portuguese_pointing_dog
            "sloughi" -> return Res.string.sloughi
            "finnish_lapponian_dog" -> return Res.string.finnish_lapponian_dog
            "hovawart" -> return Res.string.hovawart
            "bouvier_des_flandres" -> return Res.string.bouvier_des_flandres
            "kromfohrländer" -> return Res.string.kromfohrländer
            "borzoi_russian_hunting_sighthound" -> return Res.string.borzoi_russian_hunting_sighthound
            "bergamasco_shepherd_dog" -> return Res.string.bergamasco_shepherd_dog
            "italian_volpino" -> return Res.string.italian_volpino
            "bolognese" -> return Res.string.bolognese
            "neapolitan_mastiff" -> return Res.string.neapolitan_mastiff
            "italian_rough_haired_segugio" -> return Res.string.italian_rough_haired_segugio
            "cirneco_delletna" -> return Res.string.cirneco_delletna
            "italian_sighthound" -> return Res.string.italian_sighthound
            "maremma_and_the_abruzzes_sheepdog" -> return Res.string.maremma_and_the_abruzzes_sheepdog
            "italian_pointing_dog" -> return Res.string.italian_pointing_dog
            "norwegian_hound" -> return Res.string.norwegian_hound
            "spanish_hound" -> return Res.string.spanish_hound
            "chow_chow" -> return Res.string.chow_chow
            "japanese_chin" -> return Res.string.japanese_chin
            "pekingese" -> return Res.string.pekingese
            "shih_tzu" -> return Res.string.shih_tzu
            "tibetan_terrier" -> return Res.string.tibetan_terrier
            "canadian_eskimo_dog" -> return Res.string.canadian_eskimo_dog
            "samoyed" -> return Res.string.samoyed
            "hanoverian_scent_hound" -> return Res.string.hanoverian_scent_hound
            "hellenic_hound" -> return Res.string.hellenic_hound
            "bichon_frise" -> return Res.string.bichon_frise
            "pudelpointer" -> return Res.string.pudelpointer
            "bavarian_mountain_scent_hound" -> return Res.string.bavarian_mountain_scent_hound
            "chihuahua" -> return Res.string.chihuahua
            "french_tricolour_hound" -> return Res.string.french_tricolour_hound
            "french_white_black_hound" -> return Res.string.french_white_black_hound
            "wetterhoun" -> return Res.string.wetterhoun
            "stabijhoun" -> return Res.string.stabijhoun
            "dutch_shepherd_dog" -> return Res.string.dutch_shepherd_dog
            "drentsche_partridge_dog" -> return Res.string.drentsche_partridge_dog
            "fila_brasileiro" -> return Res.string.fila_brasileiro
            "landseer_european_continental_type" -> return Res.string.landseer_european_continental_type
            "lhasa_apso" -> return Res.string.lhasa_apso
            "afghan_hound" -> return Res.string.afghan_hound
            "serbian_tricolour_hound" -> return Res.string.serbian_tricolour_hound
            "tibetan_mastiff" -> return Res.string.tibetan_mastiff
            "tibetan_spaniel" -> return Res.string.tibetan_spaniel
            "deutsch_stichelhaar" -> return Res.string.deutsch_stichelhaar
            "little_lion_dog" -> return Res.string.little_lion_dog
            "xoloitzcuintle" -> return Res.string.xoloitzcuintle
            "great_dane" -> return Res.string.great_dane
            "australian_silky_terrier"-> return Res.string.australian_silky_terrier
            "norwegian_buhund"-> return Res.string.norwegian_buhund
            "mudi"-> return Res.string.mudi
            "hungarian_wire_haired_pointer"-> return Res.string.hungarian_wire_haired_pointer
            "hungarian_greyhound"-> return Res.string.hungarian_greyhound
            "hungarian_hound_transylvanian_scent_hound"-> return Res.string.hungarian_hound_transylvanian_scent_hound
            "norwegian_elkhound_grey"-> return Res.string.norwegian_elkhound_grey
            "alaskan_malamute"-> return Res.string.alaskan_malamute
            "slovakian_hound"-> return Res.string.slovakian_hound
            "bohemian_wire_haired_pointing_griffon"-> return Res.string.bohemian_wire_haired_pointing_griffon
            "cesky_terrier"-> return Res.string.cesky_terrier
            "atlas_mountain_dog_aidi"-> return Res.string.atlas_mountain_dog_aidi
            "pharaoh_hound"-> return Res.string.pharaoh_hound
            "majorca_mastiff"-> return Res.string.majorca_mastiff
            "havanese"-> return Res.string.havanese
            "polish_lowland_sheepdog"-> return Res.string.polish_lowland_sheepdog
            "tatra_shepherd_dog"-> return Res.string.tatra_shepherd_dog
            "pug"-> return Res.string.pug
            "alpine_dachsbracke"-> return Res.string.alpine_dachsbracke
            "akita"-> return Res.string.akita
            "shiba"-> return Res.string.shiba
            "japanese_terrier"-> return Res.string.japanese_terrier
            "tosa"-> return Res.string.tosa
            "hokkaido"-> return Res.string.hokkaido
            "japanese_spitz"-> return Res.string.japanese_spitz
            "chesapeake_bay_retriever"-> return Res.string.chesapeake_bay_retriever
            "mastiff"-> return Res.string.mastiff
            "norwegian_lundehund"-> return Res.string.norwegian_lundehund
            "hygen_hound"-> return Res.string.hygen_hound
            "halden_hound"-> return Res.string.halden_hound
            "norwegian_elkhound_black"-> return Res.string.norwegian_elkhound_black
            "saluki"-> return Res.string.saluki
            "siberian_husky"-> return Res.string.siberian_husky
            "bearded_collie"-> return Res.string.bearded_collie
            "norfolk_terrier"-> return Res.string.norfolk_terrier
            "canaan_dog"-> return Res.string.canaan_dog
            "greenland_dog"-> return Res.string.greenland_dog
            "brazilian_tracker"-> return Res.string.brazilian_tracker
            "norrbottenspitz"-> return Res.string.norrbottenspitz
            "croatian_shepherd_dog"-> return Res.string.croatian_shepherd_dog
            "karst_shepherd_dog"-> return Res.string.karst_shepherd_dog
            "montenegrin_mountain_hound"-> return Res.string.montenegrin_mountain_hound
            "old_danish_pointing_dog"-> return Res.string.old_danish_pointing_dog
            "grand_griffon_vendeen"-> return Res.string.grand_griffon_vendeen
            "coton_de_tulear"-> return Res.string.coton_de_tulear
            "lapponian_herder"-> return Res.string.lapponian_herder
            "spanish_greyhound"-> return Res.string.spanish_greyhound
            "american_staffordshire_terrier"-> return Res.string.american_staffordshire_terrier
            "australian_cattle_dog"-> return Res.string.australian_cattle_dog
            "chinese_crested_dog"-> return Res.string.chinese_crested_dog
            "icelandic_sheepdog"-> return Res.string.icelandic_sheepdog
            "beagle_harrier"-> return Res.string.beagle_harrier
            "eurasian"-> return Res.string.eurasian
            "dogo_argentino"-> return Res.string.dogo_argentino
            "australian_kelpie"-> return Res.string.australian_kelpie
            "otterhound"-> return Res.string.otterhound
            "harrier"-> return Res.string.harrier
            "collie_smooth" -> return Res.string.collie_smooth
            "border_collie" -> return Res.string.border_collie
            "romagna_water_dog" -> return Res.string.romagna_water_dog
            "german_hound" -> return Res.string.german_hound
            "black_and_tan_coonhound" -> return Res.string.black_and_tan_coonhound
            "american_water_spaniel" -> return Res.string.american_water_spaniel
            "irish_glen_of_imaal_terrier" -> return Res.string.irish_glen_of_imaal_terrier
            "american_foxhound" -> return Res.string.american_foxhound
            "russian_european_laika" -> return Res.string.russian_european_laika
            "east_siberian_laika" -> return Res.string.east_siberian_laika
            "west_siberian_laika" -> return Res.string.west_siberian_laika
            "azawakh" -> return Res.string.azawakh
            "dutch_smoushond" -> return Res.string.dutch_smoushond
            "shar_pei" -> return Res.string.shar_pei
            "peruvian_hairless_dog" -> return Res.string.peruvian_hairless_dog
            "saarloos_wolfhond" -> return Res.string.saarloos_wolfhond
            "nova_scotia_duck_tolling_retriever" -> return Res.string.saarloos_wolfhond
            "dutch_schapendoes" -> return Res.string.dutch_schapendoes
            "nederlandse_kooikerhondje" -> return Res.string.nederlandse_kooikerhondje
            "broholmer" -> return Res.string.broholmer
            "french_white_and_orange_hound" -> return Res.string.french_white_and_orange_hound
            "kai" -> return Res.string.kai
            "kishu" -> return Res.string.kishu
            "shikoku" -> return Res.string.shikoku
            "wirehaired_slovakian_pointer" -> return Res.string.wirehaired_slovakian_pointer
            "majorca_shepherd_dog" -> return Res.string.majorca_shepherd_dog
            "great_anglo_french_tricolour_hound" -> return Res.string.great_anglo_french_tricolour_hound
            "great_anglo_french_white_and_black_hound" -> return Res.string.great_anglo_french_white_and_black_hound
            "great_anglo_french_white_orange_hound" -> return Res.string.great_anglo_french_white_orange_hound
            "medium_sized_anglo_french_hound" -> return Res.string.medium_sized_anglo_french_hound
            "south_russian_shepherd_dog" -> return Res.string.south_russian_shepherd_dog
            "russian_black_terrier" -> return Res.string.russian_black_terrier
            "caucasian_shepherd_dog" -> return Res.string.caucasian_shepherd_dog
            "canarian_warren_hound" -> return Res.string.canarian_warren_hound
            "irish_red_and_white_setter" -> return Res.string.irish_red_and_white_setter
            "kangal_shepherd_dog" -> return Res.string.kangal_shepherd_dog
            "czechoslovakian_wolfdog" -> return Res.string.czechoslovakian_wolfdog
            "polish_greyhound" -> return Res.string.polish_greyhound
            "korea_jindo_dog" -> return Res.string.korea_jindo_dog
            "central_asia_shepherd_dog" -> return Res.string.central_asia_shepherd_dog
            "spanish_water_dog" -> return Res.string.spanish_water_dog
            "italian_short_haired_segugio" -> return Res.string.italian_short_haired_segugio
            "thai_ridgeback_dog" -> return Res.string.thai_ridgeback_dog
            "parson_russell_terrier" -> return Res.string.parson_russell_terrier
            "saint_miguel_cattle_dog" -> return Res.string.saint_miguel_cattle_dog
            "brazilian_terrier" -> return Res.string.brazilian_terrier
            "australian_shepherd" -> return Res.string.australian_shepherd
            "italian_cane_corso" -> return Res.string.italian_cane_corso
            "american_akita" -> return Res.string.american_akita
            "jack_russell_terrier" -> return Res.string.jack_russell_terrier
            "presa_canario" -> return Res.string.presa_canario
            "white_swiss_shepherd_dog" -> return Res.string.white_swiss_shepherd_dog
            "taiwan_dog" -> return Res.string.taiwan_dog
            "romanian_mioritic_shepherd_dog" -> return Res.string.romanian_mioritic_shepherd_dog
            "romanian_carpathian_shepherd_dog" -> return Res.string.romanian_carpathian_shepherd_dog
            "australian_stumpy_tail_cattle_dog" -> return Res.string.australian_stumpy_tail_cattle_dog
            "russian_toy" -> return Res.string.russian_toy
            "cimarrón_uruguayo" -> return Res.string.cimarrón_uruguayo
            "polish_hunting_dog" -> return Res.string.polish_hunting_dog
            "bosnian_and_herzegovinian_croatian_shepherd_dog" -> return Res.string.bosnian_and_herzegovinian_croatian_shepherd_dog
            "danish_swedish_farmdog" -> return Res.string.danish_swedish_farmdog
            "romanian_bucovina_shepherd" -> return Res.string.romanian_bucovina_shepherd
            "thai_bangkaew_dog" -> return Res.string.thai_bangkaew_dog
            "miniature_bull_terrier" -> return Res.string.miniature_bull_terrier
            "lancashire_heeler" -> return Res.string.lancashire_heeler
            "segugio_maremmano" -> return Res.string.segugio_maremmano
            "kintamani_bali_dog" -> return Res.string.kintamani_bali_dog
            "prague_ratter" -> return Res.string.prague_ratter
            "bohemian_shepherd_dog" -> return Res.string.bohemian_shepherd_dog
            "yakutian_laika" -> return Res.string.yakutian_laika
            "estonian_hound" -> return Res.string.estonian_hound
            "miniature_american_shepherd" -> return Res.string.miniature_american_shepherd
            "transmontano_mastiff" -> return Res.string.transmontano_mastiff
            "continental_bulldog" -> return Res.string.continental_bulldog
            "valencian_terrier" -> return Res.string.valencian_terrier
            else -> return Res.string.app_name
        }
    }
}