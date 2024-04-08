package com.group.petSitter.domain.order;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrder is a Querydsl query type for Order
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrder extends EntityPathBase<Order> {

    private static final long serialVersionUID = 1091111916L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrder order = new QOrder("order1");

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final EnumPath<OrderStatus> orderStatus = createEnum("orderStatus", OrderStatus.class);

    public final com.group.petSitter.domain.payment.QPayment payment;

    public final com.group.petSitter.domain.user.QUser user;

    public final StringPath uuid = createString("uuid");

    public QOrder(String variable) {
        this(Order.class, forVariable(variable), INITS);
    }

    public QOrder(Path<? extends Order> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrder(PathMetadata metadata, PathInits inits) {
        this(Order.class, metadata, inits);
    }

    public QOrder(Class<? extends Order> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.payment = inits.isInitialized("payment") ? new com.group.petSitter.domain.payment.QPayment(forProperty("payment"), inits.get("payment")) : null;
        this.user = inits.isInitialized("user") ? new com.group.petSitter.domain.user.QUser(forProperty("user")) : null;
    }

}

