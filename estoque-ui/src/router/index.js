import { createRouter, createWebHistory } from 'vue-router'

import ProductsView from '../views/ProductsView.vue'
import MovementView from '../views/MovementView.vue'
import MovementHistView from '../views/MovementHistView.vue'
import ProductsByTypeView from '../views/ProductsByTypeView.vue'
import ProfitView from '../views/ProfitView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', name: 'products', component: ProductsView },
    { path: '/movement', name: 'movement', component: MovementView },
    { path: '/history', name: 'history', component: MovementHistView },
    { path: '/products-by-type', name: 'productsByType', component: ProductsByTypeView },
    { path: '/profit', name: 'profit', component: ProfitView },
  ],
})

export default router
