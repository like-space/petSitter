package com.group.petSitter.domain.petSitter;

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

    private static final long serialVersionUID = -154591404L;

    public static final QPetSitter petSitter = new QPetSitter("petSitter");

    public final ListPath<com.group.petSitter.domain.pet.Pet, com.group.petSitter.domain.pet.QPet> pets = this.<com.group.petSitter.domain.pet.Pet, com.group.petSitter.domain.pet.QPet>createList("pets", com.group.petSitter.domain.pet.Pet.class, com.group.petSitter.domain.pet.QPet.class, PathInits.DIRECT2);

    public final NumberPath<Long> petSitterId = createNumber("petSitterId", Long.class);

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

