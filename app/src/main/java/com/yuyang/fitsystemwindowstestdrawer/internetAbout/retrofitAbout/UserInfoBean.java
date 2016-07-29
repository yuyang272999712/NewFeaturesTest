package com.yuyang.fitsystemwindowstestdrawer.internetAbout.retrofitAbout;

/**
 * Created by yuyang on 16/7/29.
 */
public class UserInfoBean {
    private String login;
    private Integer id;
    private String avatar_url;
    private String gravatar_id;
    private String url;
    private String html_url;
    private String followers_url;
    private String following_url;
    private String gists_url;
    private String starred_url;
    private String subscriptions_url;
    private String organizations_url;
    private String repos_url;
    private String events_url;
    private String received_events_url;
    private String type;
    private Boolean site_admin;
    private String name;
    private String company;
    private String blog;
    private String location;
    private String email;
    private Boolean hireable;
    private Object bio;
    private Integer public_repos;
    private Integer public_gists;
    private Integer followers;
    private Integer following;
    private String created_at;
    private String updated_at;

    @Override
    public String toString() {
        return "login='" + login + "\'\n" +
                "id=" + id + "\n" +
                "url='" + url + "\'\n" +
                "location='" + location + "\'\n" +
                "email='" + email + "\'\n" +
                "created_at='" + created_at + "\'";
    }

    /**
     *
     * @return
     * The login
     */
    public String getLogin() {
        return login;
    }

    /**
     *
     * @param login
     * The login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The avatar_url
     */
    public String getAvatar_url() {
        return avatar_url;
    }

    /**
     *
     * @param avatar_url
     * The avatar_url
     */
    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    /**
     *
     * @return
     * The gravatar_id
     */
    public String getGravatar_id() {
        return gravatar_id;
    }

    /**
     *
     * @param gravatar_id
     * The gravatar_id
     */
    public void setGravatar_id(String gravatar_id) {
        this.gravatar_id = gravatar_id;
    }

    /**
     *
     * @return
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @return
     * The html_url
     */
    public String getHtml_url() {
        return html_url;
    }

    /**
     *
     * @param html_url
     * The html_url
     */
    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    /**
     *
     * @return
     * The followers_url
     */
    public String getFollowers_url() {
        return followers_url;
    }

    /**
     *
     * @param followers_url
     * The followers_url
     */
    public void setFollowers_url(String followers_url) {
        this.followers_url = followers_url;
    }

    /**
     *
     * @return
     * The following_url
     */
    public String getFollowing_url() {
        return following_url;
    }

    /**
     *
     * @param following_url
     * The following_url
     */
    public void setFollowing_url(String following_url) {
        this.following_url = following_url;
    }

    /**
     *
     * @return
     * The gists_url
     */
    public String getGists_url() {
        return gists_url;
    }

    /**
     *
     * @param gists_url
     * The gists_url
     */
    public void setGists_url(String gists_url) {
        this.gists_url = gists_url;
    }

    /**
     *
     * @return
     * The starred_url
     */
    public String getStarred_url() {
        return starred_url;
    }

    /**
     *
     * @param starred_url
     * The starred_url
     */
    public void setStarred_url(String starred_url) {
        this.starred_url = starred_url;
    }

    /**
     *
     * @return
     * The subscriptions_url
     */
    public String getSubscriptions_url() {
        return subscriptions_url;
    }

    /**
     *
     * @param subscriptions_url
     * The subscriptions_url
     */
    public void setSubscriptions_url(String subscriptions_url) {
        this.subscriptions_url = subscriptions_url;
    }

    /**
     *
     * @return
     * The organizations_url
     */
    public String getOrganizations_url() {
        return organizations_url;
    }

    /**
     *
     * @param organizations_url
     * The organizations_url
     */
    public void setOrganizations_url(String organizations_url) {
        this.organizations_url = organizations_url;
    }

    /**
     *
     * @return
     * The repos_url
     */
    public String getRepos_url() {
        return repos_url;
    }

    /**
     *
     * @param repos_url
     * The repos_url
     */
    public void setRepos_url(String repos_url) {
        this.repos_url = repos_url;
    }

    /**
     *
     * @return
     * The events_url
     */
    public String getEvents_url() {
        return events_url;
    }

    /**
     *
     * @param events_url
     * The events_url
     */
    public void setEvents_url(String events_url) {
        this.events_url = events_url;
    }

    /**
     *
     * @return
     * The received_events_url
     */
    public String getReceived_events_url() {
        return received_events_url;
    }

    /**
     *
     * @param received_events_url
     * The received_events_url
     */
    public void setReceived_events_url(String received_events_url) {
        this.received_events_url = received_events_url;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The site_admin
     */
    public Boolean getSite_admin() {
        return site_admin;
    }

    /**
     *
     * @param site_admin
     * The site_admin
     */
    public void setSite_admin(Boolean site_admin) {
        this.site_admin = site_admin;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The company
     */
    public String getCompany() {
        return company;
    }

    /**
     *
     * @param company
     * The company
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     *
     * @return
     * The blog
     */
    public String getBlog() {
        return blog;
    }

    /**
     *
     * @param blog
     * The blog
     */
    public void setBlog(String blog) {
        this.blog = blog;
    }

    /**
     *
     * @return
     * The location
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     * @param location
     * The location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The hireable
     */
    public Boolean getHireable() {
        return hireable;
    }

    /**
     *
     * @param hireable
     * The hireable
     */
    public void setHireable(Boolean hireable) {
        this.hireable = hireable;
    }

    /**
     *
     * @return
     * The bio
     */
    public Object getBio() {
        return bio;
    }

    /**
     *
     * @param bio
     * The bio
     */
    public void setBio(Object bio) {
        this.bio = bio;
    }

    /**
     *
     * @return
     * The public_repos
     */
    public Integer getPublic_repos() {
        return public_repos;
    }

    /**
     *
     * @param public_repos
     * The public_repos
     */
    public void setPublic_repos(Integer public_repos) {
        this.public_repos = public_repos;
    }

    /**
     *
     * @return
     * The public_gists
     */
    public Integer getPublic_gists() {
        return public_gists;
    }

    /**
     *
     * @param public_gists
     * The public_gists
     */
    public void setPublic_gists(Integer public_gists) {
        this.public_gists = public_gists;
    }

    /**
     *
     * @return
     * The followers
     */
    public Integer getFollowers() {
        return followers;
    }

    /**
     *
     * @param followers
     * The followers
     */
    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    /**
     *
     * @return
     * The following
     */
    public Integer getFollowing() {
        return following;
    }

    /**
     *
     * @param following
     * The following
     */
    public void setFollowing(Integer following) {
        this.following = following;
    }

    /**
     *
     * @return
     * The created_at
     */
    public String getCreated_at() {
        return created_at;
    }

    /**
     *
     * @param created_at
     * The created_at
     */
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    /**
     *
     * @return
     * The updated_at
     */
    public String getUpdated_at() {
        return updated_at;
    }

    /**
     *
     * @param updated_at
     * The updated_at
     */
    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
