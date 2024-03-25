package no.ntnu.idi.idatt2105.quizopia.backend.model;

import java.time.LocalDateTime;

public class History {

    private Long history_id;
    private LocalDateTime completedAt;
    private Integer score;
    private Integer rating;
    private String feedbackText;
    private Long user_id; // Foreign key reference to Users
    private Long quiz_id; // Foreign key reference to Quiz

    // Constructors
    public History() {
    }

    public History(Long history_id, LocalDateTime completedAt, Integer score, Integer rating, String feedbackText, Long user_id, Long quiz_id) {
        this.history_id = history_id;
        this.completedAt = completedAt;
        this.score = score;
        this.rating = rating;
        this.feedbackText = feedbackText;
        this.user_id = user_id;
        this.quiz_id = quiz_id;
    }

        // Getters and Setters
        public Long getHistoryId() {
            return history_id;
        }
    
        public void setHistoryId(Long history_id) {
            this.history_id = history_id;
        }
    
        public LocalDateTime getCompletedAt() {
            return completedAt;
        }
    
        public void setCompletedAt(LocalDateTime completedAt) {
            this.completedAt = completedAt;
        }
    
        public Integer getScore() {
            return score;
        }
    
        public void setScore(Integer score) {
            this.score = score;
        }
    
        public Integer getRating() {
            return rating;
        }
    
        public void setRating(Integer rating) {
            this.rating = rating;
        }
    
        public String getFeedbackText() {
            return feedbackText;
        }
    
        public void setFeedbackText(String feedbackText) {
            this.feedbackText = feedbackText;
        }
    
        public Long getUserId() {
            return user_id;
        }
    
        public void setUserId(Long user_id) {
            this.user_id = user_id;
        }
    
        public Long getQuizId() {
            return quiz_id;
        }
    
        public void setQuizId(Long quiz_id) {
            this.quiz_id = quiz_id;
        }
    
        @Override
        public String toString() {
            return "History{" +
                    "history_id=" + history_id +
                    ", completedAt=" + completedAt +
                    ", score=" + score +
                    ", rating=" + rating +
                    ", feedbackText='" + feedbackText + '\'' +
                    ", user_id=" + user_id +
                    ", quiz_id=" + quiz_id +
                    '}';
        }
    }
    
