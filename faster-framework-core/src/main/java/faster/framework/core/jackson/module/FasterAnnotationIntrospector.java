package faster.framework.core.jackson.module;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.stream.StreamUtil;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.json.PackageVersion;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import faster.framework.core.jackson.annotation.Alias;
import faster.framework.core.jackson.annotation.AliasId;
import faster.framework.core.jackson.expland.JsonFieldExpandDetail;
import faster.framework.core.jackson.expland.JsonFieldExpandSerializer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.property.PropertyNamer;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FasterAnnotationIntrospector extends AnnotationIntrospector {
    public static final FasterAnnotationIntrospector INSTANCE = new FasterAnnotationIntrospector();
    private final Cache<Annotation, JsonFieldExpandDetail> _CACHE = Caffeine.newBuilder().build();

    @Override
    public Version version() {
        return PackageVersion.VERSION;
    }

    @Override
    public Object findSerializer(Annotated annotated) {
        if (annotated instanceof AnnotatedMethod) {
            AnnotatedMethod annotatedMethod = (AnnotatedMethod) annotated;
            Iterable<Annotation> annotations = annotatedMethod.getAllAnnotations().annotations();
            List<JsonFieldExpandDetail> details = StreamUtil.of(annotations)
                    .map(it -> this._CACHE.get(it, JsonFieldExpandDetail::from))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            if (CollUtil.isNotEmpty(details)) {
                return new JsonFieldExpandSerializer(annotated.getType(), details);
            }
        }
        return null;
    }

    @Override
    public List<PropertyName> findPropertyAliases(Annotated annotated) {
        Alias alias = this._findAnnotation(annotated, Alias.class);
        AliasId aliasId = this._findAnnotation(annotated, AliasId.class);
        if (alias == null && aliasId == null) {
            return null;
        }
        String[] aliasValues = alias == null ? new String[]{AliasId.Expander.ID} : alias.value();
        boolean deserializeFirst = alias == null ? aliasId.deserializeFirst() : alias.deserializeFirst();
        List<PropertyName> result = Arrays.stream(aliasValues)
                .map(PropertyName::construct)
                .collect(Collectors.toList());
        String name = annotated.getName();
        String property = PropertyNamer.methodToProperty(name);
        if (deserializeFirst) {
            result.add(PropertyName.construct(property));
        } else {
            result.add(0, PropertyName.construct(property));
        }
        return result;
    }
}
