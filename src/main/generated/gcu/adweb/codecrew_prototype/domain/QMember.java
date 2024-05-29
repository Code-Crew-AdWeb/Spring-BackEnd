package gcu.adweb.codecrew_prototype.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -240929447L;

    public static final QMember member = new QMember("member1");

    public final StringPath gitUrl = createString("gitUrl");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath phoneNumber = createString("phoneNumber");

    public final StringPath profileImageUrl = createString("profileImageUrl");

    public final StringPath userId = createString("userId");

    public final StringPath username = createString("username");

    public final StringPath userPw = createString("userPw");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

