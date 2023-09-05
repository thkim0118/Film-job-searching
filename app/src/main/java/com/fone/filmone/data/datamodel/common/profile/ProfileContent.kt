package com.fone.filmone.data.datamodel.common.profile

import androidx.annotation.Keep
import com.fone.filmone.data.datamodel.common.jobopenings.Type
import com.fone.filmone.data.datamodel.common.user.Career
import com.fone.filmone.data.datamodel.common.user.Category
import com.fone.filmone.data.datamodel.common.user.Domain
import com.fone.filmone.data.datamodel.common.user.Gender
import java.util.Date

interface DefaultProfileContent {
    val age: Int
    val birthday: String
    val career: Career
    val categories: List<Category>
    val details: String
    val domains: List<Domain>
    val email: String
    val gender: Gender
    val height: Int
    val hookingComment: String
    val id: Int
    val isWant: Boolean
    val name: String
    val profileUrl: String
    val profileUrls: List<String>
    val sns: String
    val specialty: String
    val type: Type
    val viewCount: Int
    val weight: Int
    val createdAt: Date
    val careerDetail: String
}

@Keep
data class ProfileContent(
    override val age: Int,
    override val birthday: String,
    override val career: Career,
    override val categories: List<Category>,
    override val details: String,
    override val domains: List<Domain>,
    override val email: String,
    override val gender: Gender,
    override val height: Int,
    override val hookingComment: String,
    override val id: Int,
    override val isWant: Boolean,
    override val name: String,
    override val profileUrl: String,
    override val profileUrls: List<String>,
    override val sns: String,
    override val specialty: String,
    override val type: Type,
    override val viewCount: Int,
    override val weight: Int,
    override val createdAt: Date,
    override val careerDetail: String,
) : DefaultProfileContent

@Keep
data class ProfileDetailContent(
    override val age: Int,
    override val birthday: String,
    override val career: Career,
    override val categories: List<Category>,
    override val details: String,
    override val domains: List<Domain>,
    override val email: String,
    override val gender: Gender,
    override val height: Int,
    override val hookingComment: String,
    override val id: Int,
    override val isWant: Boolean,
    override val name: String,
    override val profileUrl: String,
    override val profileUrls: List<String>,
    override val sns: String,
    override val specialty: String,
    override val type: Type,
    override val viewCount: Int,
    override val weight: Int,
    override val createdAt: Date,
    override val careerDetail: String,
    val userNickname: String
) : DefaultProfileContent
