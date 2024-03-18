package org.example.igotthese.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPokemon is a Querydsl query type for Pokemon
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPokemon extends EntityPathBase<Pokemon> {

    private static final long serialVersionUID = 928164176L;

    public static final QPokemon pokemon = new QPokemon("pokemon");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final ListPath<DemandSeal, QDemandSeal> demandSeals = this.<DemandSeal, QDemandSeal>createList("demandSeals", DemandSeal.class, QDemandSeal.class, PathInits.DIRECT2);

    public final ListPath<HoldingSeal, QHoldingSeal> holdingSeals = this.<HoldingSeal, QHoldingSeal>createList("holdingSeals", HoldingSeal.class, QHoldingSeal.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath pokemonName = createString("pokemonName");

    public final ListPath<SupplySeal, QSupplySeal> supplySeals = this.<SupplySeal, QSupplySeal>createList("supplySeals", SupplySeal.class, QSupplySeal.class, PathInits.DIRECT2);

    public QPokemon(String variable) {
        super(Pokemon.class, forVariable(variable));
    }

    public QPokemon(Path<? extends Pokemon> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPokemon(PathMetadata metadata) {
        super(Pokemon.class, metadata);
    }

}

