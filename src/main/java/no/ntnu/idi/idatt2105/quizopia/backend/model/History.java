package no.ntnu.idi.idatt2105.quizopia.backend.model;

import java.time.LocalDateTime;

public class History {

    private Long historyId;
    private LocalDateTime completedAt;
    private Integer score;
    private Integer rating;
    private String feedbackText;
    private Long userId; // Foreign key reference to Users
    private Long quizId; // Foreign key reference to Quiz

    // Constructors
    public History() {
    }

    public History(Long historyId, LocalDateTime completedAt, Integer score, Integer rating, String feedbackText, Long userId, Long quizId) {
        this.historyId = historyId;
        this.completedAt = completedAt;
        this.score = score;
        this.rating = rating;
        this.feedbackText = feedbackText;
        this.userId = userId;
        this.quizId = quizId;
    }

        // Getters and Setters
        public Long getHistoryId() {
            return historyId;
        }
    
        public void setHistoryId(Long historyId) {
            this.historyId = historyId;
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
            return userId;
        }
    
        public void setUserId(Long userId) {
            this.userId = userId;
        }
    
        public Long getQuizId() {
            return quizId;
        }
    
        public void setQuizId(Long quizId) {
            this.quizId = quizId;
        }
    
        @Override
        public String toString() {
            return "History{" +
                    "historyId=" + historyId +
                    ", completedAt=" + completedAt +
                    ", score=" + score +
                    ", rating=" + rating +
                    ", feedbackText='" + feedbackText + '\'' +
                    ", userId=" + userId +
                    ", quizId=" + quizId +
                    '}';
        }
    }
    
