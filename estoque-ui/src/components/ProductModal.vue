<script setup>
import { ref, onMounted } from 'vue'
import { X, Loader2 } from 'lucide-vue-next'
import api from '../api/index'

const props = defineProps({
  product: {
    type: Object,
    default: null,
  },
})

const emit = defineEmits(['close', 'product-added'])

const loading = ref(false)
const tipos = ref([])

// 1. Adicionado o campo 'id' no form para bater com seu ProductUpdateRequest
const form = ref({
  id: null,
  description: '',
  productType: '',
  supplierPrice: null,
  stockQuantity: null,
})

const fetchTypes = async () => {
  try {
    const response = await api.get('products/types')
    tipos.value = response.data
  } catch (error) {
    console.warn('Endpoint de tipos não encontrado.', error)
  }
}

// 2. Lógica para preencher os valores se o produto existir
onMounted(async () => {
  await fetchTypes()

  if (props.product) {
    // Mapeamos os dados vindos da prop para o formulário reativo
    form.value = {
      id: props.product.id,
      description: props.product.description,
      productType: props.product.productType,
      supplierPrice: props.product.supplierPrice,
      stockQuantity: props.product.stockQuantity,
    }
  }
})

const fecharModal = () => {
  emit('close')
}

const submitForm = async () => {
  try {
    loading.value = true

    if (props.product) {
      // 3. PUT agora envia o form completo (incluindo o ID no corpo) para /products
      await api.put('/products', form.value)
    } else {
      // POST para criação
      await api.post('/products', form.value)
    }

    emit('product-added')
    fecharModal()
  } catch (error) {
    console.error('Erro ao salvar produto:', error)
    alert('Erro ao salvar produto. Verifique os campos e tente novamente.')
  } finally {
    loading.value = false
  }
}

const formatPrice = () => {
  if (form.value.supplierPrice) {
    form.value.supplierPrice = parseFloat(form.value.supplierPrice).toFixed(2)
  }
}
</script>

<template>
  <div class="fixed inset-0 bg-black/50 z-50 flex items-center justify-center p-4">
    <div
      class="bg-white rounded-xl shadow-xl w-full max-w-lg overflow-hidden border border-slate-200"
    >
      <div class="flex items-center justify-between px-6 py-4 border-b border-slate-100">
        <h3 class="text-lg font-bold text-slate-800">
          {{ props.product ? 'Editar Produto' : 'Cadastrar Produto' }}
        </h3>
        <button
          @click="fecharModal"
          class="text-slate-400 hover:text-slate-600 transition-colors cursor-pointer"
        >
          <X class="w-5 h-5" />
        </button>
      </div>

      <div class="p-6">
        <form @submit.prevent="submitForm" class="space-y-5">
          <div>
            <label class="block text-sm font-medium text-slate-700 mb-1">Nome</label>
            <input
              v-model="form.description"
              type="text"
              required
              placeholder="Nome do produto"
              class="w-full px-4 py-2.5 border border-slate-200 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none transition-all text-sm"
            />
          </div>

          <div>
            <label class="block text-sm font-medium text-slate-700 mb-1">Tipo</label>
            <select
              v-model="form.productType"
              required
              class="w-full px-4 py-2.5 border border-slate-200 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none transition-all text-sm bg-white"
            >
              <option value="" disabled>Selecione um tipo...</option>
              <option v-for="tipo in tipos" :key="tipo" :value="tipo">
                {{ tipo }}
              </option>
            </select>
          </div>

          <div class="grid grid-cols-2 gap-4">
            <div>
              <label class="block text-sm font-medium text-slate-700 mb-1">Preço Fornecedor</label>
              <input
                v-model.number="form.supplierPrice"
                type="number"
                step="0.01"
                min="0"
                @blur="formatPrice"
                required
                placeholder="0.00"
                class="w-full px-4 py-2.5 border border-slate-200 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none transition-all text-sm"
              />
            </div>
            <div>
              <label class="block text-sm font-medium text-slate-700 mb-1">Quantidade</label>
              <input
                v-model.number="form.stockQuantity"
                type="number"
                required
                class="w-full px-4 py-2.5 border border-slate-200 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none transition-all text-sm"
              />
            </div>
          </div>

          <div class="pt-4">
            <button
              type="submit"
              :disabled="loading"
              class="w-full py-3 text-sm font-medium text-white bg-blue-600 hover:bg-blue-700 rounded-lg transition-colors flex items-center justify-center gap-2 disabled:opacity-70 disabled:cursor-not-allowed cursor-pointer"
            >
              <Loader2 v-if="loading" class="w-4 h-4 animate-spin" />
              <span>{{ props.product ? 'Salvar Alterações' : 'Cadastrar Produto' }}</span>
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>
