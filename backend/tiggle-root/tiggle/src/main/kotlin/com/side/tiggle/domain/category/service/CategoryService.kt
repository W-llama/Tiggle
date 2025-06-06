package com.side.tiggle.domain.category.service

import com.side.tiggle.domain.category.dto.CategoryDto
import com.side.tiggle.domain.category.model.Category
import com.side.tiggle.domain.category.repository.CategoryRepository
import com.side.tiggle.domain.member.service.MemberService
import com.side.tiggle.global.exception.NotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository,
    private val memberService: MemberService
) {
    fun createCategory(dto: CategoryDto): Category {
        val member = memberService.getMember(dto.memberId);
        return categoryRepository.save(dto.toEntity(member))
    }

    fun getCategory(categoryId: Long): Category {
        return categoryRepository.findById(categoryId)
            .orElseThrow { NotFoundException() }
    }

    fun getAllCategory(): List<Category> {
        return categoryRepository.findAll()
    }

    fun getCategoryByMemberIdAndDefaults(memberId: Long): List<Category> {
        return categoryRepository.findCategoryByMemberIdAndDefaults(memberId, true)
    }

    fun updateCategory(id: Long, dto: CategoryDto): Category {
        val category = categoryRepository.findById(id)
            .orElseThrow { NotFoundException() }
        category.apply {
            name = dto.name
            defaults = dto.defaults
        }
        return categoryRepository.save(category)
    }

    fun deleteCategory(categoryId: Long): Category {
        val category = categoryRepository.findById(categoryId)
            .orElseThrow { NotFoundException() }
        category.apply {
            deleted = true
            deletedAt = LocalDateTime.now()
        }
        return categoryRepository.save(category)
    }
//    fun deleteCategory(categoryId: Long) {

//        categoryRepository.deleteById(categoryId)
//    }
}
