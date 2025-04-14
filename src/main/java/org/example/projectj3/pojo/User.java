package org.example.projectj3.pojo;

public class User {
    private int userId;
    private String userName;
    private String email;
    private String password;
    private boolean isPremium;

    /**
     * Constructor for User
     * @param userName
     * @param email
     * @param password
     * @param isPremium
     */
    public User(String userName, String email, String password, boolean isPremium) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.isPremium = isPremium;
    }

    public User() {}

    /**
     * Getter for userId
     * @return userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Setter for userId
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Getter for userName
     * @return userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter for userName
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Getter for email
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for email
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter for password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for isPremium
     * @return isPremium
     */
    public boolean isPremium() {
        return isPremium;
    }

    /**
     * Setter for isPremium
     * @param premium
     */
    public void setPremium(boolean premium) {
        isPremium = premium;
    }

    /**
     * Override toString method
     * @return String
     */
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", isPremium=" + isPremium +
                '}';
    }
}
