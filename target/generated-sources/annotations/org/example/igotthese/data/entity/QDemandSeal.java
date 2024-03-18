package org.example.igotthese.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDemandSeal is a Querydsl query type for DemandSeal
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDemandSeal extends EntityPathBase<DemandSeal> {

    private static final long serialVersionUID = -1149242037L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDemandSeal demandSeal = new QDemandSeal("demandSeal");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final QPokemon pokemon;

    public final QPost post;

    public QDemandSeal(String variable) {
        this(DemandSeal.class, forVariable(variable), INITS);
    }

    public QDemandSeal(Path<? extends DemandSeal> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDemandSeal(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDemandSeal(PathMetadata metadata, PathInits inits) {
        this(DemandSeal.class, metadata, inits);
    }

    public QDemandSeal(Class<? extends DemandSeal> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.pokemon = inits.isInitialized("pokemon") ? new QPokemon(forProperty("pokemon")) : null;
        this.post = inits.isInitialized("post") ? new QPost(forProperty("post"), inits.get("post")) : null;
    }

}

