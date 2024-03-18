package org.example.igotthese.data.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "user")
@ToString(of = {"id", "userName", "holdingSeals"})
public class User extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @NotNull
    @Column(unique = true)
    private String userName;

    @OneToMany(mappedBy = "user")
    private List<HoldingSeal> holdingSeals = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public void addHoldingSeal(HoldingSeal holdingSeal) {
        holdingSeals.add(holdingSeal);
        holdingSeal.confirmUser(this);
    }

    public void removeHoldingSeal(HoldingSeal holdingSeal) {
        holdingSeals.remove(holdingSeal);
        holdingSeal.confirmUser(null);
    }

    public void setPost(Post post) {
        if (this.post != null) {
            this.post.setUser(null);
        }
        this.post = post;
        if (post != null) {
            post.setUser(this);
        }
    }

    public static User create(String userName, List<HoldingSeal> holdingSeals) {
        User user = new User();
        user.userName = userName;
        for (HoldingSeal holdingSeal : holdingSeals) {
            user.addHoldingSeal(holdingSeal);
        }
        return user;
    }

    public void change(String userName, List<HoldingSeal> holdingSeals) {
        changeUserName(userName);
        changeHoldingSeals(holdingSeals);
    }

    public void changeUserName(String username){
        userName = username;
    }

    public void changeHoldingSeals(List<HoldingSeal> holdingSeals) {
        List<HoldingSeal> oldHoldingSeals = this.holdingSeals.stream()
                .filter(holdingSeal -> holdingSeals.stream()
                        .noneMatch(Predicate.isEqual(holdingSeal)))
                .collect(Collectors.toList());

        List<HoldingSeal> newHoldingSeals = holdingSeals.stream()
                .filter(holdingSeal -> this.holdingSeals.stream()
                        .noneMatch(Predicate.isEqual(holdingSeal)))
                .collect(Collectors.toList());

        for (HoldingSeal holdingSeal : oldHoldingSeals) {
            removeHoldingSeal(holdingSeal);
        }

        for (HoldingSeal holdingSeal : newHoldingSeals) {
            addHoldingSeal(holdingSeal);
        }
    }

//    public void delete() {
//        if (post != null) {
//            post.delete();
//            setPost(null);
//        }
//
//        for (HoldingSeal holdingSeal : holdingSeals) {
//            removeHoldingSeal(holdingSeal);
//        }
//    }
}
