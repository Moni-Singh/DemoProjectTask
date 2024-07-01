package com.example.taskdemo.gallerymodels.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class GalleryData {
    private String id;
    private String slug;
    @SerializedName("alternative_slugs")
    private Map<String, String> alternativeSlugs;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("promoted_at")
    private String promotedAt;
    private int width;
    private int height;
    private String color;
    @SerializedName("blur_hash")
    private String blurHash;
    private String description;
    @SerializedName("alt_description")
    private String altDescription;
    private List<Breadcrumb> breadcrumbs;
    private Urls urls;
    private Links links;
    private int likes;
    @SerializedName("liked_by_user")
    private boolean likedByUser;
    @SerializedName("current_user_collections")
    private List<Object> currentUserCollections;
    private Object sponsorship;
    @SerializedName("topic_submissions")
    private Map<String, TopicSubmission> topicSubmissions;
    @SerializedName("asset_type")
    private String assetType;
    private User user;
    private List<Tag> tags;

    // Constructor for required fields
    public GalleryData(String id, String slug, Map<String, String> alternativeSlugs,
                       String createdAt, String updatedAt, String promotedAt,
                       int width, int height, String color, String blurHash,
                       String description, String altDescription,
                       List<Breadcrumb> breadcrumbs, Urls urls, Links links,
                       int likes, boolean likedByUser, List<Object> currentUserCollections,
                       Object sponsorship, Map<String, TopicSubmission> topicSubmissions,
                       String assetType, User user, List<Tag> tags) {
        this.id = id;
        this.slug = slug;
        this.alternativeSlugs = alternativeSlugs;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.promotedAt = promotedAt;
        this.width = width;
        this.height = height;
        this.color = color;
        this.blurHash = blurHash;
        this.description = description;
        this.altDescription = altDescription;
        this.breadcrumbs = breadcrumbs;
        this.urls = urls;
        this.links = links;
        this.likes = likes;
        this.likedByUser = likedByUser;
        this.currentUserCollections = currentUserCollections;
        this.sponsorship = sponsorship;
        this.topicSubmissions = topicSubmissions;
        this.assetType = assetType;
        this.user = user;
        this.tags = tags;
    }

    // Getters and Setters for all fields

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Map<String, String> getAlternativeSlugs() {
        return alternativeSlugs;
    }

    public void setAlternativeSlugs(Map<String, String> alternativeSlugs) {
        this.alternativeSlugs = alternativeSlugs;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getPromotedAt() {
        return promotedAt;
    }

    public void setPromotedAt(String promotedAt) {
        this.promotedAt = promotedAt;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBlurHash() {
        return blurHash;
    }

    public void setBlurHash(String blurHash) {
        this.blurHash = blurHash;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAltDescription() {
        return altDescription;
    }

    public void setAltDescription(String altDescription) {
        this.altDescription = altDescription;
    }

    public List<Breadcrumb> getBreadcrumbs() {
        return breadcrumbs;
    }

    public void setBreadcrumbs(List<Breadcrumb> breadcrumbs) {
        this.breadcrumbs = breadcrumbs;
    }

    public Urls getUrls() {
        return urls;
    }

    public void setUrls(Urls urls) {
        this.urls = urls;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public boolean isLikedByUser() {
        return likedByUser;
    }

    public void setLikedByUser(boolean likedByUser) {
        this.likedByUser = likedByUser;
    }

    public List<Object> getCurrentUserCollections() {
        return currentUserCollections;
    }

    public void setCurrentUserCollections(List<Object> currentUserCollections) {
        this.currentUserCollections = currentUserCollections;
    }

    public Object getSponsorship() {
        return sponsorship;
    }

    public void setSponsorship(Object sponsorship) {
        this.sponsorship = sponsorship;
    }

    public Map<String, TopicSubmission> getTopicSubmissions() {
        return topicSubmissions;
    }

    public void setTopicSubmissions(Map<String, TopicSubmission> topicSubmissions) {
        this.topicSubmissions = topicSubmissions;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
