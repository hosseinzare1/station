package  ir.romina.hossein.core.base.domain.use_cases

interface BaseUseCase<R, P> {
    suspend fun call(arg: P): R
}
