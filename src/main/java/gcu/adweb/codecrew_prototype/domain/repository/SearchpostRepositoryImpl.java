package gcu.adweb.codecrew_prototype.domain.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gcu.adweb.codecrew_prototype.domain.entity.Post;
import gcu.adweb.codecrew_prototype.domain.dto.PostDto.*;
import gcu.adweb.codecrew_prototype.domain.dto.TagDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static gcu.adweb.codecrew_prototype.domain.entity.QMember.member;
import static gcu.adweb.codecrew_prototype.domain.entity.QPost.post;
import static gcu.adweb.codecrew_prototype.domain.entity.QTag.tag;


@Repository
public class SearchpostRepositoryImpl extends QuerydslRepositorySupport implements SearchPostRepository {


    private final JPAQueryFactory jpaQueryFactory;

    public SearchpostRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Post.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }


    //키워드로 검색
    @Override
    public Page<PostResponseDto> findPostByKeyword(String keyword, Pageable pageable,Long memberId) {

        // Tag 가 컬렉션이라서 루트 1번, 컬렉션 1번 쿼리를 날려야함

        // 루트 조회(toOne 코드를 모두 한번에 조회)

        List<PostResponseDto> result = findPosts(keyword,pageable,memberId);

        // Tag 컬렉션을 MAP 한방에 조회

        Map<Long,List<TagDto>> tagMap = findTagMap(toTagIds(result));

        // 루프를 돌면서 컬렉션 추가(추가 쿼리 실행X)
        result.forEach(p -> p.setTagList(tagMap.get(p.getPostId())));

       return new PageImpl<>(result,pageable,result.size());


//        List<PostResponseDto> findPostResponseDtos = jpaQueryFactory
//                .select(Projections.fields(PostResponseDto.class,
//                                post.id, post.title, post.content, member.username, post.createdAt, post.postImg, post.code, post.privacy
//
//                        )
//                )
//                .from(post)
//                .innerJoin(post.member,member)
//                .innerJoin(post.tagList,tag)
//                .where(containKeyword(keyword))
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .orderBy(
//                        post.createdAt.asc()
//                ).fetch();




//        return new PageImpl<>(findPostResponseDtos, pageable, findPostResponseDtos.size());

    }

//    @Override
//    public Page<PostResponseDto> postList() {
//
//
//
//    }

    public List<PostResponseDto> findPosts(String keyword, Pageable pageable,Long memberId) {
        return jpaQueryFactory
                .select(Projections.fields(
                        PostResponseDto.class,post.id,post.title,post.content,
                                member.username,post.createdDate,post.postImg,post.privacy,post.reference,post.codeAfterUpdate,post.codeBeforeUpdate
                )
                )
                .from(post)
                .innerJoin(post.member,member)
                .where(containKeyword(keyword))
                .where(isMemberId(memberId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(
                        post.createdDate.asc()
                )
                .fetch();

    }

    private Map<Long,List<TagDto>> findTagMap(List<Long> tagIds) {
        List<TagDto> tags = jpaQueryFactory.select(Projections.fields(TagDto.class,tag.id,tag.name))
                .from(tag)
                .where(tag.id.in(tagIds))
                .fetch();

        return tags.stream()
                .collect(Collectors.groupingBy(TagDto::getTagId));

    }

    private List<Long> toTagIds(List<PostResponseDto> result) {
        return result.stream()
                .map((p->p.getPostId()))
                .collect(Collectors.toList());
    }

        BooleanExpression containKeyword (String keyword){
            if (keyword == null || keyword.isEmpty())
                return null;
            return post.content.contains(keyword).or(post.title.contains(keyword));
        }
        BooleanExpression isMemberId(Long memberId) {
        if(memberId == null) {
            return null;
        }
        return member.id.eq(memberId);
        }
    }

