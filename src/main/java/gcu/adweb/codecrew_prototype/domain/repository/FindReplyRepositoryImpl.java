package gcu.adweb.codecrew_prototype.domain.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gcu.adweb.codecrew_prototype.domain.dto.ReplyDto;
import gcu.adweb.codecrew_prototype.domain.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static gcu.adweb.codecrew_prototype.domain.entity.QMember.member;
import static gcu.adweb.codecrew_prototype.domain.entity.QReply.reply;

@Repository
public class FindReplyRepositoryImpl extends QuerydslRepositorySupport implements FindReplyRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public FindReplyRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Reply.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    //댓글, 대댓글 뽑아오기
    @Override
    public Page<ReplyDto.ReplyGetResponseDto> findPostReplies(Long postId, Pageable pageable) {
        List<Tuple> replyTuples = jpaQueryFactory
                .select(reply, member.username)
                .from(reply)
                .join(reply.member, member)
                .where(reply.post.id.eq(postId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(
                        reply.date.asc()
                )
                .fetch();
        Map<Long, ReplyDto.ReplyGetResponseDto> replyDtoMap = new HashMap<>();
        for(Tuple tuple : replyTuples){
            Reply replyTmp = tuple.get(reply);
            String username = tuple.get(member.username);
            //댓글

                ReplyDto.ReplyGetResponseDto replyGetResponseDto =
                        ReplyDto.ReplyGetResponseDto.toReplyGetResponseDto(replyTmp.getId(), username, replyTmp.getContent(), replyTmp.getDate());
                replyDtoMap.put(replyTmp.getId(), replyGetResponseDto);

        }

        List<ReplyDto.ReplyGetResponseDto> parentReplies = new ArrayList<>(replyDtoMap.values());

        return new PageImpl<>(parentReplies, pageable, parentReplies.size());
    }
}
