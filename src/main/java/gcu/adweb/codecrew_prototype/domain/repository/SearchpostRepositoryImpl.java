package gcu.adweb.codecrew_prototype.domain.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gcu.adweb.codecrew_prototype.domain.entity.Post;
import gcu.adweb.codecrew_prototype.domain.dto.PostDto.*;
import gcu.adweb.codecrew_prototype.domain.dto.TagDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static gcu.adweb.codecrew_prototype.domain.entity.QMember.member;
import static gcu.adweb.codecrew_prototype.domain.entity.QPost.post;
import static gcu.adweb.codecrew_prototype.domain.entity.QTag.tag;


@Repository
@Slf4j
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

        Map<Long,List<TagDto>> tagMap = findTagMap(toPostIds(result));


        // 루프를 돌면서 컬렉션 추가(추가 쿼리 실행X)
        result.forEach(p -> p.setTagList(tagMap.get(p.getId())));

       return new PageImpl<>(result,pageable,result.size());

    }

    @Override
    public PostResponseDto findOnePost(Long postId) {

        Post findPost = jpaQueryFactory.selectFrom(post)
                .innerJoin(post.tagList, tag)
                .where(post.id.eq(postId))
                .fetchOne( );



        assert findPost != null;
        List<TagDto> tagDtoList = findPost.getTagList().stream( )
                .map(tag->TagDto.toTagDto(tag,tag.getPost()))
                .collect(Collectors.toList());
            return PostResponseDto.toPostResponseDto(findPost,tagDtoList);

    }


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


    public Page<PostResponseDto> findPostByTag(Long tagId,Pageable pageable) {

        List<Long> postIds = jpaQueryFactory.select(tag.post.id)
                .from(tag)
                .where(tag.id.eq(tagId))
                .fetch( );


        List<PostResponseDto> postResponseDtos = jpaQueryFactory.select(Projections.fields(
                                PostResponseDto.class, post.id, post.title, post.content,
                                member.username, post.createdDate, post.postImg, post.privacy, post.reference, post.codeAfterUpdate, post.codeBeforeUpdate
                        )
                )
                .from(post)
                .innerJoin(post.member,member)
                .where(post.id.in(postIds))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(
                        post.createdDate.asc()
                )
                .fetch( );


        Map<Long,List<TagDto>> tagMap = findTagMap(postIds);



        // 루프를 돌면서 컬렉션 추가(추가 쿼리 실행X)
        postResponseDtos.forEach(p -> p.setTagList(tagMap.get(p.getId())));


        return new PageImpl<>(postResponseDtos,pageable,postResponseDtos.size());



    }

    private Map<Long,List<TagDto>> findTagMap(List<Long> postIds) {

        List<TagDto> tagDtos = jpaQueryFactory.select(Projections.fields(TagDto.class,tag.id,tag.name,tag.post.id.as("postId")))
                .from(tag)
                .where(tag.post.id.in(postIds))
                .fetch();

        return tagDtos.stream()
                .collect(Collectors.groupingBy(TagDto::getPostId));

    }

    private List<Long> toPostIds(List<PostResponseDto> result) {
        return result.stream()
                .map((p->p.getId()))
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



