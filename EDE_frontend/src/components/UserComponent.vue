<template>
    <div class="text-center">
        <img v-bind:src="user.picture" class="mx-auto rounded-full shadow-lg dark:shadow-black/20" alt=""
            style="max-width: 20px" />
        <div flex justify-center items-center>
            <p class=" font-bold">{{ user.name }}</p>
            <font-awesome-icon class=" hover:cursor-pointer" @click="logout" icon="fa-solid fa-right-from-bracket" />
        </div>
    </div>
</template>
<script>
import { mapActions } from 'vuex'
import { mapState } from 'vuex'
import { googleLogout } from 'vue3-google-login';
export default {
    name: 'UserComponent',
    computed: {
        ...mapState(['loggedIn', 'user']),
    },
    methods: {
        ...mapActions(['setLoggedIn', 'setUser','setToken']),
        logout(){
            googleLogout()
            this.setLoggedIn(false)
            this.setUser(null)
            this.setToken(null)
        }

    }
}

</script>
<style></style>