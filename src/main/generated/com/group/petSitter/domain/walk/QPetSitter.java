package com.group.petSitter.domain.walk;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPetSitter is a Querydsl query type for PetSitter
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPetSitter extends EntityPathBase<PetSitter> {

    private static final long serialVersionUID = 920272479L;

    public static final QPetSitter petSitter = new QPetSitter("petSitter");

    public final com.group.petSitter.global.QBaseTimeEntity _super = new com.group.petSitter.global.QBaseTimeEntity(this);

    public final StringPath address = createString("address");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath password = createString("password");

    public final NumberPath<Long> petSitterId = createNumber("petSitterId", Long.class);

    public final ListPath<com.group.petSitter.domain.review.Review, com.group.petSitter.domain.review.QReview> reviews = this.<com.group.petSitter.domain.review.Review, com.group.petSitter.domain.review.QReview>createList("reviews", com.group.petSitter.domain.review.Review.class, com.group.petSitter.domain.review.QReview.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final StringPath username = createString("username");

    public QPetSitter(String variable) {
        super(PetSitter.class, forVariable(variable));
    }

    public QPetSitter(Path<? extends PetSitter> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPetSitter(PathMetadata metadata) {
        super(PetSitter.class, metadata);
    }

}

