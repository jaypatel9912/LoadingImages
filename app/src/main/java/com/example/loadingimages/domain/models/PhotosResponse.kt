package com.example.loadingimages.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotosResponseItem(
    val alt_description: String? = null,
    val alternative_slugs: AlternativeSlugs?,
    val asset_type: String? = null,
    val blur_hash: String? = null,
    val breadcrumbs: List<Breadcrumbs>?,
    val color: String? = null,
    val created_at: String? = null,
    val current_user_collections: List<CurrentUserCollection>?,
    val description: String? = null,
    val height: Int?,
    val id: String? = null,
    val liked_by_user: Boolean?,
    val likes: Int?,
    val links: Links?,
    val promoted_at: String? = null,
    val slug: String? = null,
    val sponsorship: SponsorShip? = null,
    val topic_submissions: TopicSubmissions?,
    val updated_at: String? = null,
    val urls: Urls?,
    val user: User?,
    val width: Int?,
) : java.io.Serializable

@Serializable
data class AlternativeSlugs(
    val de: String? = null,
    val en: String? = null,
    val es: String? = null,
    val fr: String? = null,
    val `it`: String? = null,
    val ja: String? = null,
    val ko: String? = null,
    val pt: String? = null,
) : java.io.Serializable

@Serializable
data class Animals(

    val approved_on: String? = null,
    val status: String? = null,
) : java.io.Serializable

@Serializable
data class ArchitectureInterior(
    val status: String? = null,
) : java.io.Serializable

@Serializable
data class BlackAndWhite(
    val approved_on: String? = null,
    val status: String? = null,
) : java.io.Serializable

@Serializable
data class Experimental(
    val status: String? = null,
) : java.io.Serializable

@Serializable
data class FashionBeauty(
    val status: String? = null,
) : java.io.Serializable

@Serializable
data class Links(
    val download: String? = null,
    val download_location: String? = null,
    val html: String? = null,
    val self: String? = null,
) : java.io.Serializable

@Serializable
data class LinksX(
    val followers: String? = null,
    val following: String? = null,
    val html: String? = null,
    val likes: String? = null,
    val photos: String? = null,
    val portfolio: String? = null,
    val self: String? = null,
) : java.io.Serializable

@Serializable
data class Nature(
    val approved_on: String? = null,
    val status: String? = null,
) : java.io.Serializable

@Serializable
data class People(
    val approved_on: String? = null,
    val status: String? = null,
) : java.io.Serializable

@Serializable
data class ProfileImage(
    val large: String? = null,
    val medium: String? = null,
    val small: String? = null,
) : java.io.Serializable

@Serializable
data class Social(
    val instagram_username: String? = null,
    val paypal_email: String? = null,
    val portfolio_url: String? = null,
    val twitter_username: String? = null,
) : java.io.Serializable

@Serializable
data class StreetPhotography(
    val approved_on: String? = null,
    val status: String? = null,
) : java.io.Serializable

@Serializable
data class TopicSubmissions(
    val animals: Animals? = null,
    val `architecture-interior`: ArchitectureInterior? = null,
    val `black-and-white`: BlackAndWhite? = null,
    val experimental: Experimental? = null,
    val `fashion-beauty`: FashionBeauty? = null,
    val nature: Nature? = null,
    val people: People? = null,
    val `street-photography`: StreetPhotography? = null,
    val travel: Travel? = null,
    val wallpapers: Wallpapers? = null,
) : java.io.Serializable

@Serializable
data class Travel(
    val approved_on: String? = null,
    val status: String? = null,
) : java.io.Serializable

@Serializable
data class Urls(
    val full: String? = null,
    val raw: String? = null,
    val regular: String? = null,
    val small: String? = null,
    val small_s3: String? = null,
    val thumb: String? = null,
) : java.io.Serializable

@Serializable
data class User(
    val accepted_tos: Boolean? = null,
    val bio: String? = null,
    val first_name: String? = null,
    val for_hire: Boolean? = null,
    val id: String? = null,
    val instagram_username: String? = null,
    val last_name: String? = null,
    val links: LinksX? = null,
    val location: String? = null,
    val name: String? = null,
    val portfolio_url: String? = null,
    val profile_image: ProfileImage? = null,
    val social: Social? = null,
    val total_collections: Int? = null,
    val total_illustrations: Int? = null,
    val total_likes: Int? = null,
    val total_photos: Int? = null,
    val total_promoted_illustrations: Int? = null,
    val total_promoted_photos: Int? = null,
    val twitter_username: String? = null,
    val updated_at: String? = null,
    val username: String? = null,
) : java.io.Serializable

@Serializable
data class Wallpapers(
    @SerialName("approved_on")
    val approved_on_wallpaper: String? = null,
    val status: String? = null,
) : java.io.Serializable

@Serializable
data class Breadcrumbs(
    val index: Int? = null,
    val slug: String? = null,
    val title: String? = null,
    val type: String? = null,
) : java.io.Serializable

@Serializable
data class CurrentUserCollection(
    val cover_photo: String? = null,
    val id: Int? = null,
    val last_collected_at: String? = null,
    val published_at: String? = null,
    val title: String? = null,
    val updated_at: String? = null,
    val user: User? = null,
) : java.io.Serializable