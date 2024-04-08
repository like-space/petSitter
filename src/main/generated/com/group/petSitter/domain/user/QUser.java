package com.group.petSitter.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 240305932L;

    public static final QUser user = new QUser("user");

    public final com.group.petSitter.global.QBaseTimeEntity _super = new com.group.petSitter.global.QBaseTimeEntity(this);

    public final StringPath address = createString("address");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final StringPath nickname = createString("nickname");

    public final ListPath<com.group.petSitter.domain.pet.Pet, com.group.petSitter.domain.pet.QPet> pets = this.<com.group.petSitter.domain.pet.Pet, com.group.petSitter.domain.pet.QPet>createList("pets", com.group.petSitter.domain.pet.Pet.class, com.group.petSitter.domain.pet.QPet.class, PathInits.DIRECT2);

    public final StringPath provider = createString("provider");

    public final StringPath providerId = createString("providerId");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final EnumPath<UserGrade> userGrade = createEnum("userGrade", UserGrade.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final EnumPath<UserRole> userRole = createEnum("userRole", UserRole.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

