package org.example.igotthese.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSupplySeal is a Querydsl query type for SupplySeal
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSupplySeal extends EntityPathBase<SupplySeal> {

    private static final long serialVersionUID = -1628192273L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSupplySeal supplySeal = new QSupplySeal("supplySeal");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final QPokemon pokemon;

    public final QPost post;

    public QSupplySeal(String variable) {
        this(SupplySeal.class, forVariable(variable), INITS);
    }

    public QSupplySeal(Path<? extends SupplySeal> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSupplySeal(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSupplySeal(PathMetadata metadata, PathInits inits) {
        this(SupplySeal.class, metadata, inits);
    }

    public QSupplySeal(Class<? extends SupplySeal> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.pokemon = inits.isInitialized("pokemon") ? new QPokemon(forProperty("pokemon")) : null;
        this.post = inits.isInitialized("post") ? new QPost(forProperty("post"), inits.get("post")) : null;
    }

}

