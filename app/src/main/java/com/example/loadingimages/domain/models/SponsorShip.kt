package com.example.loadingimages.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class SponsorShip(
    val impression_urls: List<String?>? = null,
    val sponsor: Sponsor?=null,
    val tagline: String?=null,
    val tagline_url: String?=null,
) : java.io.Serializable

@Serializable
data class SponsorLinks(
    val followers: String?=null,
    val following: String?=null,
    val html: String?=null,
    val likes: String?=null,
    val photos: String?=null,
    val portfolio: String?=null,
    val self: String?=null,
) : java.io.Serializable

@Serializable
data class Sponsor(
    val accepted_tos: Boolean?=null,
    val bio: String?=null,
    val first_name: String?=null,
    val for_hire: Boolean?=null,
    val id: String?=null,
    val instagram_username: String?=null,
    val last_name: String?=null,
    val links: SponsorLinks?=null,
    val location: String?=null,
    val name: String?=null,
    val portfolio_url: String?=null,
    val profile_image: SponsorProfileImage?=null,
    val social: SponsorSocial?=null,
    val total_collections: Int?=null,
    val total_illustrations: Int?=null,
    val total_likes: Int?=null,
    val total_photos: Int?=null,
    val total_promoted_illustrations: Int?=null,
    val total_promoted_photos: Int?=null,
    val twitter_username: String?=null,
    val updated_at: String?=null,
    val username: String?=null,
) : java.io.Serializable

@Serializable
data class SponsorSocial(
    val instagram_username: String?=null,
    val paypal_email: String?=null,
    val portfolio_url: String?=null,
    val twitter_username: String?=null,
) : java.io.Serializable

@Serializable
data class SponsorProfileImage(
    val large: String?=null,
    val medium: String?=null,
    val small: String?=null,
) : java.io.Serializable