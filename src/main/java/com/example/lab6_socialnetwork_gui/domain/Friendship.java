package com.example.lab6_socialnetwork_gui.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Friendship extends Entity<Long>{
    private final long idU1;
    private final long idU2;
    private final LocalDateTime date;
    private FriendshipStatus status;

    public Friendship(long idU1, long idU2, LocalDateTime date) {
        this.idU1 = idU1;
        this.idU2 = idU2;
        this.date = date;
        status = FriendshipStatus.PENDING;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friendship that = (Friendship) o;
        return idU1 == that.idU1 && idU2 == that.idU2;
    }

    public FriendshipStatus getStatus() {
        return status;
    }

    public void setStatus(FriendshipStatus status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idU1, idU2);
    }

    public long getIdU1() {
        return idU1;
    }

    public long getIdU2() {
        return idU2;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "idU1=" + idU1 +
                ", idU2=" + idU2 +
                ", date=" + date +
                ", status=" + status +
                '}';
    }
}
