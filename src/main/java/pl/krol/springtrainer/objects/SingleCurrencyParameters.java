package pl.krol.springtrainer.objects;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.NonNull;
import pl.krol.springtrainer.annotations.AllowedValues;
import pl.krol.springtrainer.annotations.CurrencyCode;

import java.time.LocalDate;

@Getter
public class SingleCurrencyParameters {
    @NonNull
    @AllowedValues(values = {"A", "B", "C"})
    private String table;
    @NonNull
    @CurrencyCode
    private String code;
    @Nullable
    @AllowedValues(values = {"last", "today"})
    private String lastOrToday;
    @Nullable
    private Long topCount;
    @Nullable
    private LocalDate startDate;
    @Nullable
    private LocalDate endDate;

    public SingleCurrencyParameters(Builder builder) {
        this.table = builder.table;
        this.code = builder.code;
        this.lastOrToday = builder.lastOrToday;
        this.topCount = builder.topCount;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
    }

    public static class Builder {
        private String table;
        private String code;
        private String lastOrToday;
        private Long topCount;
        private LocalDate startDate;
        private LocalDate endDate;
        public Builder table(String table) {
            this.table = table;
            return this;
        }
        public Builder code(String code) {
            this.code = code;
            return this;
        }
        public Builder lastOrToday(String lastOrToday) {
            this.lastOrToday = lastOrToday;
            return this;
        }
        public Builder topCount(Long topCount) {
            this.topCount = topCount;
            return this;
        }
        public Builder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }
        public Builder endDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }
        public SingleCurrencyParameters build() {
            return new SingleCurrencyParameters(this);
        }
    }
}
