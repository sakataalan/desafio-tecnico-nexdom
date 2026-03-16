<script setup>
import { ref, onMounted } from 'vue'
import { Loader2 } from 'lucide-vue-next'
import api from '../api/index'
import { useRouter } from 'vue-router'

const router = useRouter()
const products = ref([])
const loading = ref(false)

const form = ref({
  productId: '',
  movementType: 'ENTRADA',
  quantity: null,
  salePrice: null,
})

const fetchProducts = async () => {
  try {
    const response = await api.get('/products')
    products.value = response.data || []
  } catch (error) {
    console.error('Erro ao carregar produtos:', error)
  }
}

onMounted(() => {
  fetchProducts()
})

const handleTypeChange = () => {
  if (form.value.movementType === 'ENTRADA') {
    form.value.salePrice = null
  }
}

const priceFormat = () => {
  if (form.value.salePrice) {
    form.value.salePrice = parseFloat(form.value.salePrice).toFixed(2)
  }
}

const submitMovement = async () => {
  if (!form.value.productId || !form.value.movementQuantity) return

  try {
    loading.value = true
    const now = new Date().toISOString().split('.')[0]

    await api.post('/stock-movement', {
      productId: form.value.productId,
      movementType: form.value.movementType,
      salePrice: form.value.salePrice,
      movementDate: now,
      movementQuantity: form.value.movementQuantity,
    })

    if (form.value.movementType === 'ENTRADA') {
      alert('Entrada registrada com sucesso!')
    } else {
      alert('Saída registrada com sucesso!')
    }

    router.push('/')
  } catch (error) {
    console.error('Erro ao registrar movimentação:', error)
    alert('Erro ao registrar movimentação.')
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <div class="max-w-2xl">
    <div class="mb-8">
      <h1 class="text-3xl font-bold text-slate-900">Movimentação</h1>
    </div>

    <div class="bg-white rounded-xl shadow-sm border border-slate-200 p-8 max-w-lg">
      <form @submit.prevent="submitMovement" class="space-y-6">
        <div>
          <label class="block text-sm font-medium text-slate-700 mb-2">Tipo de Operação</label>
          <select
            v-model="form.movementType"
            @change="handleTypeChange"
            class="w-full px-4 py-3 border border-slate-200 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none text-sm bg-white font-medium"
          >
            <option value="ENTRADA">ENTRADA</option>
            <option value="SAIDA">SAÍDA</option>
          </select>
        </div>

        <div>
          <label class="block text-sm font-medium text-slate-700 mb-2">Produto</label>
          <select
            v-model="form.productId"
            required
            class="w-full px-4 py-3 border border-slate-200 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none text-sm bg-white"
          >
            <option value="" disabled>Selecione um produto...</option>
            <option v-for="p in products" :key="p.id" :value="p.id">
              {{ p.description }}
            </option>
          </select>
        </div>

        <div class="grid grid-cols-2 gap-4">
          <div>
            <label class="block text-sm font-medium text-slate-700 mb-2">Quantidade</label>
            <input
              v-model.number="form.movementQuantity"
              type="number"
              min="1"
              required
              placeholder="0"
              class="w-full px-4 py-3 border border-slate-200 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none text-sm"
            />
          </div>
          <div>
            <label
              class="block text-sm font-medium text-slate-700 mb-2"
              :class="{ 'text-slate-700': form.movementType === 'SAIDA' }"
            >
              Valor Venda (R$)
            </label>
            <input
              v-model.number="form.salePrice"
              type="number"
              step="0.01"
              min="0"
              @blur="priceFormat"
              :disabled="form.movementType === 'ENTRADA'"
              :required="form.movementType === 'SAIDA'"
              placeholder="0.00"
              class="w-full px-4 py-3 border border-slate-200 rounded-lg focus:ring-2 outline-none text-sm transition-all disabled:bg-slate-50 disabled:text-slate-300 disabled:cursor-not-allowed focus:ring-blue-500"
            />
          </div>
        </div>

        <button
          type="submit"
          :disabled="loading"
          :class="'bg-blue-600 hover:bg-blue-700 shadow-blue-100'"
          class="w-full text-white font-bold py-3.5 rounded-lg flex items-center justify-center gap-2 transition-all shadow-md active:scale-[0.98] disabled:opacity-70 mt-4"
        >
          <Loader2 v-if="loading" class="w-5 h-5 animate-spin" />
          <span>Confirmar {{ form.movementType === 'ENTRADA' ? 'Entrada' : 'Saída' }}</span>
        </button>
      </form>
    </div>
  </div>
</template>
