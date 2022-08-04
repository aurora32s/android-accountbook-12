package com.seom.accountbook.usecase

import com.seom.accountbook.data.repository.CategoryRepository
import com.seom.accountbook.data.repository.MethodRepository
import com.seom.accountbook.data.repository.impl.CategoryRepositoryImpl
import com.seom.accountbook.data.repository.impl.MethodRepositoryImpl
import com.seom.accountbook.model.setting.SettingModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetAllSettingDataUseCase @Inject constructor(
    private val methodRepository: MethodRepository,
    private val categoryRepository: CategoryRepository
) : UseCase {

    suspend operator fun invoke(): SettingModel =
        coroutineScope {
            val categories = async { categoryRepository.getAllCategories() }
            val methods = async { methodRepository.getAllMethods() }

            SettingModel(
                methods = methods.await(),
                categories = categories.await()
            )
        }
}