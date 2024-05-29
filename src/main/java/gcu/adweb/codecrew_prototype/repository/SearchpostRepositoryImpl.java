package gcu.adweb.codecrew_prototype.repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gcu.adweb.codecrew_prototype.domain.Post;
import gcu.adweb.codecrew_prototype.dto.PostDto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;


import java.util.List;

import static gcu.adweb.codecrew_prototype.domain.QMember.member;
import static gcu.adweb.codecrew_prototype.domain.QPost.post;


@Repository
public class SearchpostRepositoryImpl extends QuerydslRepositorySupport implements SearchPostRepository {


    private final JPAQueryFactory jpaQueryFactory;

    public SearchpostRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Post.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<PostResponseDto> postListResponseDto(String keyword, Pageable pageable) {

        List<PostResponseDto> findPostResponseDtos = jpaQueryFactory
                .select(Projections.fields(PostResponseDto.class,
                                post.id, post.title, post.content, member.username, post.createdAt, post.postImg, post.likeCount, post.commentCount


                        )
                )
                .from(post)
                .innerJoin(post.member,member)
                .where(containKeyword(keyword))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(
                        post..asc()
                ).fetch();




        return new PageImpl<>(findPostResponseDtos, pageable, findPostResponseDtos.size());

    }

        BooleanExpression containKeyword (String keyword){
            if (keyword == null || keyword.isEmpty())
                return null;
            return post.postContent.contains(keyword).or(post.title.contains(keyword));
        }
    }

