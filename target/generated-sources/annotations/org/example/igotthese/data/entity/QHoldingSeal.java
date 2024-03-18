package org.example.igotthese.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHoldingSeal is a Querydsl query type for HoldingSeal
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHoldingSeal extends EntityPathBase<HoldingSeal> {

    private static final long serialVersionUID = 1072825693L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHoldingSeal holdingSeal = new QHoldingSeal("holdingSeal");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final QPokemon pokemon;

    public final QUser user;

    public QHoldingSeal(String variable) {
        this(HoldingSeal.class, forVariable(variable), INITS);
    }

    public QHoldingSeal(Path<? extends HoldingSeal> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHoldingSeal(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHoldingSeal(PathMetadata metadata, PathInits inits) {
        this(HoldingSeal.class, metadata, inits);
    }

    public QHoldingSeal(Class<? extends HoldingSeal> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.pokemon = inits.isInitialized("pokemon") ? new QPokemon(forProperty("pokemon")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

