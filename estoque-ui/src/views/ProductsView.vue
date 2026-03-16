<script setup>
import { ref, onMounted } from 'vue'
import api from '../api/index'
import ProductModal from '../components/ProductModal.vue'
import { Pencil, Trash2 } from 'lucide-vue-next'

const products = ref([])
const loading = ref(true)
const showModal = ref(false)
const selectedProduct = ref(null)

const fetchProducts = async () => {
  try {
    loading.value = true
    const response = await api.get('/products')
    products.value = response.data
  } catch (error) {
    console.error('Erro ao buscar produtos:', error)
  } finally {
    loading.value = false
  }
}

const openCreateModal = () => {
  selectedProduct.value = null
  showModal.value = true
}

const openEditModal = (product) => {
  selectedProduct.value = product
  showModal.value = true
}

const deleteProduct = async (id) => {
  if (confirm('Deseja mesmo excluir este produto?')) {
    try {
      await api.delete(`/products/${id}`)
      fetchProducts()
      alert('Produto removido com sucesso!')
    } catch (error) {
      const message =
        error.response?.data?.message ||
        'Erro ao excluir produto. Ele pode ter movimentações vinculadas.'
      alert(message)
    }
  }
}

onMounted(() => {
  fetchProducts()
})

const formatCurrency = (value) => {
  return new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(value || 0)
}
</script>

<template>
  <ProductModal
    v-if="showModal"
    :product="selectedProduct"
    @close="showModal = false"
    @product-added="fetchProducts"
  />

  <header class="h-24 flex items-center justify-between">
    <div>
      <h1 class="text-3xl font-bold text-gray-900">Produtos</h1>
    </div>

    <button
      @click="openCreateModal"
      class="bg-blue-600 text-white px-5 py-2.5 rounded-md text-sm font-medium hover:bg-blue-700 transition-colors flex items-center gap-2 shadow-sm"
    >
      <span>+</span> Novo Produto
    </button>
  </header>

  <div class="bg-white rounded-xl shadow-sm border border-slate-200 overflow-hidden">
    <div
      v-if="products.length === 0"
      class="p-12 flex flex-col items-center justify-center text-slate-500 min-h-100"
    >
      <p>Nenhum produto cadastrado ainda.</p>
    </div>

    <table v-else class="w-full text-left text-sm text-slate-600">
      <thead class="bg-slate-50 border-b border-slate-200 text-slate-700">
        <tr>
          <th class="py-4 px-6 font-semibold">ID</th>
          <th class="py-4 px-6 font-semibold">Nome</th>
          <th class="py-4 px-6 font-semibold">Tipo</th>
          <th class="py-4 px-6 font-semibold">Preço do fornecedor</th>
          <th class="py-4 px-6 font-semibold">Quantidade</th>
          <th class="py-4 px-6 font-semibold">Atualizar</th>
          <th class="py-4 px-6 font-semibold">Apagar</th>
        </tr>
      </thead>
      <tbody class="divide-y divide-slate-100">
        <tr
          v-for="product in products"
          :key="product.id"
          class="hover:bg-slate-50 transition-colors"
        >
          <td class="py-4 px-6 text-slate-400">#{{ product.id }}</td>
          <td class="py-4 px-6 font-medium text-slate-900">{{ product.description }}</td>
          <td class="py-4 px-6">
            <span class="bg-blue-100 text-blue-700 px-2 py-1 rounded text-xs font-medium">
              {{ product.productType }}
            </span>
          </td>
          <td class="py-4 px-6 font-medium">
            {{ formatCurrency(product.supplierPrice) }}
          </td>
          <td class="py-4 px-6">{{ product.stockQuantity }} un.</td>
          <td class="py-4 px-6">
            <button
              @click="openEditModal(product)"
              class="text-blue-600 hover:text-blue-800 transition-colors cursor-pointer"
            >
              <Pencil class="w-4 h-4" />
            </button>
          </td>
          <td class="py-4 px-6">
            <button
              @click="deleteProduct(product.id)"
              class="text-red-600 hover:text-red-800 transition-colors cursor-pointer"
            >
              <Trash2 class="w-4 h-4" />
            </button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>
