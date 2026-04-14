package model;

import java.time.Instant;
import java.util.Date;

public class Order {
  private int id;
  private int userId;
  private String userName;     
  private Instant createdAt;
  private String status;      
  private double total;        
  private int itemCount;       
  
  public Date getCreatedAtDate() {
        return createdAt == null ? null : Date.from(createdAt);
    }
  
  public int getId() { return id; }
  public void setId(int id) { this.id = id; }

  public int getUserId() { return userId; }
  public void setUserId(int userId) { this.userId = userId; }

  public String getUserName() { return userName; }
  public void setUserName(String userName) { this.userName = userName; }

  public Instant getCreatedAt() { return createdAt; }
  public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }

  public double getTotal() { return total; }
  public void setTotal(double total) { this.total = total; }

  public int getItemCount() { return itemCount; }
  public void setItemCount(int itemCount) { this.itemCount = itemCount; }
}
