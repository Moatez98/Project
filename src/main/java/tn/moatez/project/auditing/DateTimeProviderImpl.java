package tn.moatez.project.auditing;

import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.lang.NonNull;

import java.time.OffsetDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

public class DateTimeProviderImpl implements DateTimeProvider {

    @Override
    @NonNull
    public Optional<TemporalAccessor> getNow() {
        return Optional.of(OffsetDateTime.now());
    }
}

